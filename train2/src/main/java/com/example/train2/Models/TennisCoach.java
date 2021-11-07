package com.example.train2.Models;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// prototype creates a new bean every time
// singleton creates one bean and shares it between instances
@Component
@Scope("prototype")
// @Scope("singleton")
public class TennisCoach implements Coach {

    private final FortuneService fortuneService;

    @Autowired
    public TennisCoach(@Qualifier("sadFortuneService") FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "adfasd";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

    @PostConstruct
    public void initBean() {
        System.out.println("TennisCoach.initBean()");
    }

    @PreDestroy
    public void destroyBean() {
        System.out.println("TennisCoach.destroyBean()");
    }

}
