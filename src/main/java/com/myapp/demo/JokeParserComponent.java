package com.myapp.demo;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component 
public class JokeParserComponent {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String parse(String body, JokeType jokeType) {
        if (body == null || body.isBlank()) {
            return "No joke available";
        }

        try {
            JsonNode root = objectMapper.readTree(body);
            JsonNode node = root;

            String key1 = jokeType.key1();
            String key2 = jokeType.key2();

            if (root.isArray() && root.size() > 0) {
                node = root.get(0);
            }

            if (key2 == null || key2.isBlank()) {
                return node.path(key1).asText(body);
            }

            return node.path(key1).asText("") 
                + "\n\n"
                + node.path(key2).asText("");

        } catch (Exception e) {
            return body;
        }
    }

}
