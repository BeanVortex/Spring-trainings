package com.example.train1;

import com.example.train1.models.CricketCoach;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Train1Application {

	public static void main(String[] args) {

		try (var context = new ClassPathXmlApplicationContext("context.xml")) {
			var couch = context.getBean("myCricketCoach", CricketCoach.class);
			var couch2 = context.getBean("myCricketCoach", CricketCoach.class);
			System.out.println();
			couch2.setName("fdgs");
			System.out.println(couch.equals(couch2));
			System.out.println(couch.getDailyWorkout());
			System.out.println(couch.getDailyFortune());
			System.out.println();
		}

	}

}
