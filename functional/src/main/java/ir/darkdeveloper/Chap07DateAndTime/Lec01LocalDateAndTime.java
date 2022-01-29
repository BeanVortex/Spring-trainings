package ir.darkdeveloper.Chap07DateAndTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class Lec01LocalDateAndTime {

    public static void main(String[] args) {
        var localDate = LocalDate.of(2022, 12, 31);
        System.out.println(localDate.getYear());
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getMonth());
        System.out.println(localDate.getDayOfWeek());
        System.out.println(localDate.lengthOfMonth());

        System.out.println(LocalDate.now());

        System.out.println(localDate.get(ChronoField.DAY_OF_YEAR));

        var localTime = LocalTime.of(13, 16, 59, 10);
        System.out.printf("%d:%d:%d:%d\n",
                localTime.getHour(),
                localTime.getMinute(),
                localTime.getSecond(),
                localTime.getNano());

        var parsedDate = LocalDate.parse("2019-10-15");
        System.out.println(parsedDate);

        var parsedTime = LocalTime.parse("12:15:04");
        System.out.println(parsedTime);
    }


}
