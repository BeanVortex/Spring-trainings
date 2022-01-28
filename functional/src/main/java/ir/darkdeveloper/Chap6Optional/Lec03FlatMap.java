package ir.darkdeveloper.Chap6Optional;

import java.util.Optional;

public class Lec03FlatMap {

    public static void main(String[] args) {
        var insurance = new Insurance("bime abbasam");
        var car = Optional.of(new Car(Optional.of(insurance)));
        var person = new Person(car);
        System.out.println(getCarInsuranceName(person));
    }

    private static String getCarInsuranceName(Person person) {
        return Optional.of(person)
                .flatMap(Person::car)
                .flatMap(Car::insurance)
                .map(Insurance::name)
                .orElse("No Data");
    }


    record Person(Optional<Car> car) {
    }

    record Car(Optional<Insurance> insurance) {
    }

    record Insurance(String name) {
    }

}


