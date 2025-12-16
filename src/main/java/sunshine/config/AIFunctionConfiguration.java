package sunshine.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import sunshine.Service.WeatherServiceImpl;

import java.time.LocalDate;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class AIFunctionConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AIFunctionConfiguration.class);

    @Bean
    @Tool(description = "Calculate a date after adding days from today")
    public Function<AddDaysRequest, DateResponse> addDaysFromToday() {
        return request -> {
            LocalDate result = LocalDate.now().plusDays(request.days());
            return new DateResponse(result.toString());
        };
    }

    @Bean
    @Tool(description = "Convert weather code to description.")
    public Function<WeatherCodeReq, WeatherCodeResp> convertWeatherCodeToDescription() {
        return request -> {
            log.info("날씨 코드 {}", request.code);

            if (request.code == 0) {
                return new WeatherCodeResp("Clear sky");
            }
            if (request.code == 1) {
                return new WeatherCodeResp("Mainly clear");
            }
            if (request.code == 2) {
                return new WeatherCodeResp("partly cloudy");
            }
            if (request.code == 3) {
                return new WeatherCodeResp("overcast");
            }
            if (request.code == 45) {
                return new WeatherCodeResp("Fog");
            }
            if (request.code == 48) {
                return new WeatherCodeResp("depositing rime fog");
            }
            if (request.code == 51) {
                return new WeatherCodeResp("Drizzle: Light");
            }
            if (request.code == 53) {
                return new WeatherCodeResp("Drizzle: moderate");
            }
            if (request.code == 55) {
                return new WeatherCodeResp("Drizzle: dense intensity");
            }
            if (request.code == 56) {
                return new WeatherCodeResp("Freezing Drizzle: Light");
            }
            if (request.code == 57) {
                return new WeatherCodeResp("Freezing Drizzle: dense intensity");
            }
            if (request.code == 61) {
                return new WeatherCodeResp("Rain: Slight");
            }
            if (request.code == 63) {
                return new WeatherCodeResp("Rain: moderate");
            }
            if (request.code == 65) {
                return new WeatherCodeResp("Rain: heavy intensity");
            }
            if (request.code == 66) {
                return new WeatherCodeResp("Freezing Rain: Light");
            }
            if (request.code == 67) {
                return new WeatherCodeResp("Freezing Rain: heavy intensity");
            }
            if (request.code == 71) {
                return new WeatherCodeResp("Snow fall: Slight");
            }
            if (request.code == 73) {
                return new WeatherCodeResp("Snow fall: moderate");
            }
            if (request.code == 75) {
                return new WeatherCodeResp("Snow fall: heavy intensity");
            }
            if (request.code == 77) {
                return new WeatherCodeResp("Snow grains");
            }
            if (request.code == 80) {
                return new WeatherCodeResp("Rain showers Slight");
            }
            if (request.code == 81) {
                return new WeatherCodeResp("Rain showers moderate");
            }
            if (request.code == 82) {
                return new WeatherCodeResp("Rain showers violent");
            }
            if (request.code == 85) {
                return new WeatherCodeResp("Snow showers slight");
            }
            if (request.code == 86) {
                return new WeatherCodeResp("Snow showers heavy");
            }
            if (request.code == 95) {
                return new WeatherCodeResp("Thunderstorm: Slight or moderate");
            }
            if (request.code == 96) {
                return new WeatherCodeResp("Thunderstorm with slight");
            }
            if (request.code == 99) {
                return new WeatherCodeResp("heavy hail");
            }

            return new WeatherCodeResp("dirty");
        };
    }

    public record WeatherCodeReq(int code) {}
    public record WeatherCodeResp(String resp) {}
}
