package com.myapp.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        List<JokeType> jokeTypes = new ArrayList<>();

        for (JokeType jokeType : JokeType.defaults()) {
            jokeTypes.add(jokeType);
        }

        model.addAttribute("pageTitle", "Cheesy jokes");
        model.addAttribute("msg", "Smile! It's not that deep.");
        model.addAttribute("jokeTypes",jokeTypes);

        return "index";
    }

    @GetMapping("/getJokes")
    public String getPageTwo(@RequestParam(name = "jokeType") String jokeType,
        Model model) {
        model.addAttribute("pageTitle", "Jokes");
        model.addAttribute("jokeType", "Selected: " + jokeType.toUpperCase());
        return "jokes";
    }
}