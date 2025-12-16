package sunshine.controller;

import core.weather.object.ActorFilms;
import core.weather.object.City;
import core.weather.object.CityConverter;
import core.weather.object.WeatherResp;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.Service.AiService;
import sunshine.Service.WeatherService;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping(path = "/v1/llm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLlm(@RequestParam final String message) {
        return ResponseEntity.ok(aiService.getAiResponse(message));
    }

    @GetMapping(path = "/v1/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActorFilms> getMovies(@RequestParam final String actor) {
        return ResponseEntity.ok(aiService.getMovies(actor));
    }

    @GetMapping(path = "/v1/add-days", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatResponse> addDays(@RequestParam final int days) {
        return ResponseEntity.ok(aiService.addDays(days));
    }

}
