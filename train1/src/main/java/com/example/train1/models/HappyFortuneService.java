package com.example.train1.models;

public class HappyFortuneService implements FortuneService {

    @Override
    public String getFortune() {
        return "Today is your lucky day";
    }
    
}
