package sunshine.Service;

import core.weather.object.City;
import core.weather.object.WeatherResp;

public interface WeatherService {
    WeatherResp getCityWeather(City city, boolean llm);
}
