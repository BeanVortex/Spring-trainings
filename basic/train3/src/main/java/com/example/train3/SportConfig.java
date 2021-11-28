package com.example.train3;

import com.example.train3.Models.Coach;
import com.example.train3.Models.FortuneService;
import com.example.train3.Models.HappyFortuneService;
import com.example.train3.Models.SadFortuneService;
import com.example.train3.Models.SwimCoach;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
// @ComponentScan("com.example.train3") //optional
@PropertySource("classpath:application.properties")
public class SportConfig {

    @Bean
    public Coach swimCoach() {
        return new SwimCoach(happyFortuneService());
    }

    @Bean
    public FortuneService happyFortuneService() {
        return new HappyFortuneService();
    }

    @Bean
    public FortuneService sadFortuneService() {
        return new SadFortuneService();
    }

}
