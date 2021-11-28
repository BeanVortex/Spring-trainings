package com.example.train1.models;

public class BaseballCoach implements Coach {

    private final FortuneService fortuneService;


    public BaseballCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }
    

    @Override
    public String getDailyWorkout() {
        return "Spend 30 minutes on batting practice";
    }


    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

}
