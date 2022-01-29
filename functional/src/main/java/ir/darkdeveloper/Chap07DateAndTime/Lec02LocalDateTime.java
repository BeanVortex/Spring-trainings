package ir.darkdeveloper.Chap07DateAndTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class Lec02LocalDateTime {

    public static void main(String[] args) {
        System.out.println(LocalDateTime.of(2016, Month.JULY, 14, 22, 0, 19));
        var date = LocalDate.of(2014, 1, 11);
        var time = LocalTime.of(14, 1, 16);
        System.out.println(LocalDateTime.of(date, time));
        // returns LocalDateTime
        System.out.println(date.atTime(22, 14, 10));
        System.out.println(date.atTime(time));
        // returns LocalDateTime
        System.out.println(time.atDate(date));

    }

}