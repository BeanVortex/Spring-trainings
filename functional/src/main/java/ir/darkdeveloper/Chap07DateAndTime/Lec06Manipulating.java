package ir.darkdeveloper.Chap07DateAndTime;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Lec06Manipulating {

    public static void main(String[] args) {

        var d1 = LocalDate.of(1654, 12, 1);
        var d2 = d1.withYear(2111);
        var d3 = d2.withDayOfMonth(10);
        var d4 = d3.with(ChronoField.DAY_OF_MONTH, 25);
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
        System.out.println(d4);

        var d5 = d4.plusDays(10);
        var d6 = d5.minusYears(15);
        var d7 = d6.minus(4, ChronoUnit.MONTHS);
        System.out.println(d5);
        System.out.println(d6);
        System.out.println(d7);

    }

}
