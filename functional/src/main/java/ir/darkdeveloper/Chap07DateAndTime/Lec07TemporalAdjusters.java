package ir.darkdeveloper.Chap07DateAndTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class Lec07TemporalAdjusters {

    public static void main(String[] args) {

        var d1 = LocalDate.of(2156, 4, 10);
        var d2 = d1.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
        var d3 = d1.with(TemporalAdjusters.lastDayOfMonth());
        // and other static methods in TemporalAdjusters class

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
    }

}
