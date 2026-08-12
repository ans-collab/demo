package com.myapp.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Chuck Norris Joke 1.0");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");

            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                "https://api.chucknorris.io/jokes/random",
                HttpMethod.GET,
                request,
                String.class
            );

            String body = response.getBody();

            if (body != null && !body.isBlank()) {
                try {
                    JsonNode root = objectMapper.readTree(body);
                    String joke = root.path("value").asText("No joke available.");
                    model.addAttribute("joke", joke);
                } catch (Exception parseEx) {
                    model.addAttribute("joke", body);
                }
            } else {
                model.addAttribute("joke", "No joke available.");
            }
        } catch (Exception e) {
            model.addAttribute("joke", "Could not load joke.");
        }

        return "index";
    }
}