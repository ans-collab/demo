package com.myapp.demo;
import java.net.URI;
import java.util.List;

public record JokeType ( 
    String type,
    String displayName,
    String key1,
    String key2,
    URI uri
) { 
    public static List<JokeType> defaults() { 
        return List.of(
            new JokeType(
                "Chuck",
                "Chuck Norris jokes",
                "value",
                null,
                URI.create("https://api.chucknorris.io/jokes/random")
            ), //value
            new JokeType(
                "Dad",
                "Dad jokes",
                "setup",
                "punchline",
                URI.create("https://jokelikeadad.com/api/random") 
            ), //setup + punchline
            new JokeType(
                "Programming", 
                "Programming jokes",
                "setup",
                "punchline",
                URI.create("https://official-joke-api.appspot.com/jokes/programming/random")
                //URI.create("https://geek-jokes.sameerkumar.website/api?format=json")
            )    
        );  
    }  
}

