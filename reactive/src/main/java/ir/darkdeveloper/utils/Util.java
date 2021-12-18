package ir.darkdeveloper.utils;

import java.util.function.Consumer;

import com.github.javafaker.Faker;

import org.reactivestreams.Subscriber;

public class Util {

    private static final Faker FAKER = Faker.instance();

    public static Consumer<Object> onNext() {
        return o -> System.out.println("Received: " + o);
    }

    public static Consumer<Throwable> onError() {
        return err -> System.out.println("ERROR: " + err.getMessage());
    }

    public static Runnable onComplete(String message) {
        if (message == null)
            message = "";
        final String finalMessage = message;
        return () -> System.out.println("Completed " + finalMessage);
    }

    public static Faker faker() {
        return FAKER;
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Subscriber<Object> subscriber() {
        return new DefaultSubscriber();
    }

    public static Subscriber<Object> subscriber(String name) {
        return new DefaultSubscriber(name);
    }

}
