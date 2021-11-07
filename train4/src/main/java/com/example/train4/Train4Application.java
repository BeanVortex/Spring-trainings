package com.example.train4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Train4Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Train4Application.class, args);
	}


	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Train4Application.class);
    }

}
