package ir.darkdeveloper.Chap07DateAndTime;

import java.time.Instant;

public class Lec03Instant {
    
    public static void main(String[] args) {
        // starts from January, 1, 1970 UTC
        System.out.println(Instant.ofEpochSecond(999_999_999));
        System.out.println(Instant.ofEpochMilli(999_999_999));
        System.out.println(Instant.ofEpochSecond(3, 1_000_000_000));
        System.out.println(Instant.now());
        System.out.println(Instant.now().toEpochMilli());
    }

}