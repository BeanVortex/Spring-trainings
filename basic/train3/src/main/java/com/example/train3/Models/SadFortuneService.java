package com.example.train3.Models;

public class SadFortuneService implements FortuneService {

    @Override
    public String getFortune() {
        return "Today is not your lucky day";
    }

    
}