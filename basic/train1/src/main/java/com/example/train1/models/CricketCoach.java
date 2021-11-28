package com.example.train1.models;

public class CricketCoach implements Coach {

    private FortuneService fortuneService;
    private String name;
    private String email;

    @Override
    public String getDailyWorkout() {
        return "Practice fast bowling for 15 minutes " + name + " \n" + email;
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

    public void setFortuneService(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void initMethod() {
        System.out.println("CricketCoach.initMethod()");
    }

    public void destroyMethod() {
        System.out.println("CricketCoach.destroyMethod()");
    }

}
