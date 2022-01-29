package ir.darkdeveloper.Chap06Optional;

import java.util.Optional;

public class Lec02OptionalMap {

    public static void main(String[] args) {
        var car = new Car("Aston Martin", 100.5);
        var optCar = Optional.of(car);

        var carName = optCar.map(Car::name);
        System.out.println(carName);
    }

    record Car(String name, double price) {
    }
}


