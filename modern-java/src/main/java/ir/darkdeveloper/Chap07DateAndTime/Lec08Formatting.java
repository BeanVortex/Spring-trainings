package ir.darkdeveloper.Chap07DateAndTime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class Lec08Formatting {

    public static void main(String[] args) {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.ROOT);
        var date = LocalDate.now();
        var formattedDate = date.format(formatter);
        var parsedDate = LocalDate.parse(formattedDate, formatter);

        System.out.println("date: " + date);
        System.out.println("formattedDate: " + formattedDate);
        System.out.println("parsedDate: " + parsedDate);

        var formatter2 = new DateTimeFormatterBuilder()
                .appendText(ChronoField.YEAR)
                .appendLiteral(".")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(".")
                .appendText(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        System.out.println(date.format(formatter2));
    }

}
