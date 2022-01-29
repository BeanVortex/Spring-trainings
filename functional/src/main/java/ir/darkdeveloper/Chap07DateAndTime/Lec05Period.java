package ir.darkdeveloper.Chap07DateAndTime;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;


public class Lec05Period {
    
    public static void main(String[] args) {
        
        var d1 = LocalDate.of(2161, 5,15);
        var d2 = LocalDate.of(2000, 10,10);
        System.out.println(Period.between(d1, d2));

        var d3 = d1.plus(10, ChronoUnit.DAYS);
        System.out.println(d3);

    }

}
