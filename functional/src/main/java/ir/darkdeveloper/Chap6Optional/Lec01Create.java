package ir.darkdeveloper.Chap6Optional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class Lec01Create {

    public static void main(String[] args) {
        var car = new Car("pride", 170);
        var car2 = (Car) null;

        var opt1 = Optional.of(car);
        assertThrows(NullPointerException.class, () -> Optional.of(car2));
        var opt2 = Optional.ofNullable(car2);

        System.out.println(opt1);
        System.out.println(opt2);

        System.out.println(opt1.get());
        // throws java.util.NoSuchElementException
        System.out.println(opt2.get());


    }

    record Car(String name, double price) {
    }
}
