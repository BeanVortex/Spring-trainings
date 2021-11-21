package com.example.train6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/leaders")
    public String leaders() {
        return "leaders";
    }

    @GetMapping("/systems")
    public String systems() {
        return "systems";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied";
    }


}
