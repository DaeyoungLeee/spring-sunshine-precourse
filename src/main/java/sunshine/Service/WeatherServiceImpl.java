package sunshine.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.weather.constraints.WeatherConstraints;
import core.weather.object.City;
import core.weather.object.OpenMeteoResponse;
import core.weather.object.RecommendationResp;
import core.weather.object.WeatherResp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import core.exception.ExternalApiException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service("WeatherService")
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AiService aiService;

    @Override
    public WeatherResp getCityWeather(City city, boolean llm) {
        RecommendationResp recommendationResp = null;
        if (city.getLatitude() == 0 || city.getLongitude() == 0) {
            try {
                city = aiService.getLocationByCity(city);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        OpenMeteoResponse apiResponse = callWeatherForecast(city);
        if (llm) {
            try {
                recommendationResp = aiService.getCostumeRecommendation(apiResponse);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return convertToWeatherResp(city, llm, apiResponse, recommendationResp);
    }

    private OpenMeteoResponse callWeatherForecast(City city) {
        String url = buildWeatherUrl(city);
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                log.info("Weather API Response: {}", response);
                return objectMapper.readValue(response.toString(), OpenMeteoResponse.class);
            } else {
                log.error("Weather API error code: {}", responseCode);
                throw new ExternalApiException();
            }
        } catch (Exception e) {
            log.error("Error calling weather API", e);
            throw new ExternalApiException();
        }
    }

    private String buildWeatherUrl(City city) {
        return String.format("%s%s?latitude=%f&longitude=%f&current=temperature_2m,wind_speed_10m,wind_direction_10m,weather_code",
                WeatherConstraints.OPEN_METRO_URL,
                WeatherConstraints.FORECAST_PATH,
                city.getLatitude(),
                city.getLongitude());
    }

    private WeatherResp convertToWeatherResp(City city, boolean llm, OpenMeteoResponse apiResponse, RecommendationResp recommendationResp) {
        String summary;
        if (recommendationResp == null) {
            summary = String.format("오늘 %s의 온도는 %s도이며 바람은 %sm/s입니다.", city.getName(), apiResponse.getCurrent().getTemperature(), apiResponse.getCurrent().getWindSpeed());
        } else {
            summary = recommendationResp.recommend();
        }
        return WeatherResp.builder()
                .cityName(city.getName())
                .latitude(apiResponse.getLatitude())
                .longitude(apiResponse.getLongitude())
                .currentTemperature(apiResponse.getCurrent().getTemperature())
                .windSpeed(apiResponse.getCurrent().getWindSpeed())
                .windDirection(apiResponse.getCurrent().getWindDirection())
                .weatherCode(String.valueOf(apiResponse.getCurrent().getWeatherCode()))
                .time(apiResponse.getCurrent().getTime())
                .isLlm(llm)
                .recommendationResp(RecommendationResp.builder().recommend(summary).build())
                .build();
    }
}