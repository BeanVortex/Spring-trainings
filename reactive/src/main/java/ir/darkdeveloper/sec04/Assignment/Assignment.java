package ir.darkdeveloper.sec04.Assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

public class Assignment {

    public static void main(String[] args) throws IOException {
        FileService.read("assignment.txt")
                .take(3)
                .map(d -> {
                    return d.hashCode();
                })
                .subscribe(Util.subscriber());
    }

}

class FileService {

    private static final String PATH = "src/main/resources/assignment/sec04/";

    private static Callable<BufferedReader> reader(String filename) {
        return () -> Files.newBufferedReader(Paths.get(PATH + filename));
    }

    private static BiFunction<BufferedReader, SynchronousSink<Object>, BufferedReader> generator() {
        return (state, sink) -> {
            var str = "";
            try {
                str = state.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                sink.error(e);
            }
            Util.sleep(1);
            if (str == null)
                sink.complete();
            else
                sink.next(str);
            return state;
        };
    }

    public static Flux<Object> read(String filename) {

        return Flux.generate(reader(filename), generator());
    }

}