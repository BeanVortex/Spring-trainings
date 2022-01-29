package ir.darkdeveloper.Chap07DateAndTime;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Lec04Duration {

    public static void main(String[] args) {
        var dt1 = LocalDateTime.now();
        var dt2 = LocalDateTime.of(2012, 1, 16, 14, 25, 15);
        System.out.println(Duration.between(dt1, dt2));

        System.out.println();

        var t1 = LocalTime.of(15, 15, 16);
        var t2 = LocalTime.of(10, 15, 16);
        System.out.println(Duration.between(t1, t2));
        
        System.out.println();

        var i1 = Instant.ofEpochSecond(154641345);
        var i2 = Instant.ofEpochSecond(456603133);
        System.out.println(Duration.between(i1, i2));

        // Only works with times
        // var d1 = LocalDate.of(2161, 12,15);
        // var d2 = LocalDate.of(2000, 12,15);
        // System.out.println(Duration.between(d1, d2));
        System.out.println();

        var twoMinutes = Duration.ofMinutes(2);
        var tenHours = Duration.of(10, ChronoUnit.HOURS);
        System.out.println(twoMinutes);
        System.out.println(tenHours);

        var threeDays = Period.ofDays(3);
        var tenYearsThreeMonthsTwoDays = Period.of(10, 3, 2);
        System.out.println(threeDays);
        System.out.println(tenYearsThreeMonthsTwoDays);

    }

}
