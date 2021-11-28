package com.example.train2;

import com.example.train2.Models.Coach;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Train2Application {

	public static void main(String[] args) {
		try (var context = new ClassPathXmlApplicationContext("context.xml")) {
			var coach = context.getBean("tennisCoach", Coach.class);
			var coach2 = context.getBean("tennisCoach", Coach.class);
			System.out.println(coach == coach2);
			System.out.println(coach.getDailyWorkout());
			System.out.println(coach.getDailyFortune());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
