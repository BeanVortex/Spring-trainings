package ir.darkdeveloper.Chap6Optional;

import java.util.Optional;

public class Lec04UnWrapping {

    public static void main(String[] args) {

        var optPerson = Optional.of("DD");

        // simply gets the value even if empty(throws exception)
        System.out.println(optPerson.get());

        // if present, gets the value. if not, returns the default value
        System.out.println(optPerson.orElse("Default val"));

        // if present, gets the value. if not, does some operation and returns the calculated value (lazy)
        System.out.println(optPerson.orElseGet(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Default val";
        }));

        // if present, gets the optional of value. if not, does some operation and 
        // returns the calculated value wrapped in a optional (lazy)
        System.out.println(optPerson.or(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Optional.of("Default val");
        }));

        System.out.println(optPerson.isPresent());

        // throw custom error when the value is absent
        var optEmpty = Optional.empty();
        System.out.println(optEmpty.orElseThrow(IllegalArgumentException::new));


    }

}
