package com.myapp.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Cheesy jokes");
        model.addAttribute("msg", "Smile! It's not that deep.");
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