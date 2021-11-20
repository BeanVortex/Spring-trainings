package com.example.train6;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Train6Application {


    public static void main(String[] args) {
        SpringApplication.run(Train6Application.class, args);
        log.error("Error");
        log.warn("info");
        log.info("info");
    }

}
