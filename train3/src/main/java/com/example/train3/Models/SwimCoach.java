package com.example.train3.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class SwimCoach implements Coach {
    
    private final FortuneService fortuneService;

    @Value("${foo.email}")
    private String email;

    @Value("${foo.name}")
    private String name;

    @Autowired
    public SwimCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Swim for 5 rounds " + email + " " + name;
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
    
}
