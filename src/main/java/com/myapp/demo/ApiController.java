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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ApiController {

    private static final Logger logger =
        LoggerFactory.getLogger(ApiController.class); 

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/get")
    public String getJoke(@RequestParam String uri, @RequestParam String jokeType) 
    {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");

            ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
            );

            String body = response.getBody();

            logger.info("API response: {}", body);

            if (body != null && !body.isBlank()) {
                try {
                    JsonNode root = objectMapper.readTree(body);

                    if("dad".equalsIgnoreCase(jokeType)) {
                        return root.path("setup").asText("")
                        + "\n\n"
                        + root.path("punchline").asText("");
                    }

                    return root.path("value").asText(body);
                } catch (Exception parseEx) {
                    return body;
                }
            }  else {  
                return "No joke available";  
            }
        } catch (Exception exception) {
            logger.error("Failed to load joke", exception);
            return "Could not load joke.";
        }
    }

}
