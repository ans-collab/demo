package com.myapp.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/get")
    public String get(@RequestParam(name = "name", required = false, defaultValue = "world") String name) {
        return "Hello " + name;
    }

}
