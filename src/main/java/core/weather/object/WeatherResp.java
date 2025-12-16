package core.weather.object;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Builder
public class WeatherResp {
    private String cityName;
    private double latitude;
    private double longitude;
    private double currentTemperature;
    private double windSpeed;
    private int windDirection;
    private String weatherCode;
    private String time;
    private boolean isLlm;
    private RecommendationResp recommendationResp;
}