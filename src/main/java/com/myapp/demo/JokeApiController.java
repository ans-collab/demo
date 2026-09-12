package com.myapp.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class JokeApiController {

    private final JokeParserComponent jokeParserComponent;

    private static final Logger logger =
        LoggerFactory.getLogger(JokeApiController.class); 

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    JokeApiController(JokeParserComponent jokeParserComponent) {
        this.jokeParserComponent = jokeParserComponent;
    }

    @GetMapping("/get")
    public String getJoke(@RequestParam String jokeType) 
    {
        try {
            JokeType selectedType = JokeType.defaults().stream()
                .filter(type -> type.type().equals(jokeType))
                .findFirst()
                .orElse(null);
           
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");

            ResponseEntity<String> response = restTemplate.exchange(
                selectedType.uri().toString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
            );

            String body = response.getBody();
            logger.info("URI : {}", selectedType.uri().toString());
            logger.info("API response: {}", body);

            return jokeParserComponent.parse(body, selectedType);
        } catch (Exception exception) {
            logger.error("Failed to load joke", exception);
            return "Could not load joke.";
        }
    }

}
