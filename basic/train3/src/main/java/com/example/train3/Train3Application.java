package com.example.train3;

import com.example.train3.Models.Coach;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Train3Application {

	public static void main(String[] args) {
		var context = new AnnotationConfigApplicationContext(SportConfig.class);
		var coach = context.getBean("swimCoach", Coach.class);
		System.out.println(coach.getDailyWorkout());
		System.out.println(coach.getDailyFortune());
		context.close();
	}

}
