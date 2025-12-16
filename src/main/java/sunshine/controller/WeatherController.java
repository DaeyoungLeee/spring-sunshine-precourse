package sunshine.controller;

import core.weather.object.City;
import core.weather.object.CityConverter;
import core.weather.object.WeatherResp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.Service.WeatherService;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final CityConverter cityConverter;

    @GetMapping(path = "/v1/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResp> getCityWeather(@RequestParam final String city,
                                                      @RequestParam(defaultValue = "false") String llm) {
        City cityObj = cityConverter.convert(city);
        boolean includeLlm;
        if ("true".equalsIgnoreCase(llm)) {
            includeLlm = true;
        } else {
            includeLlm = false;
        }
        return ResponseEntity.ok(weatherService.getCityWeather(cityObj, includeLlm));
    }

}
