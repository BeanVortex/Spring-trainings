package ir.darkdeveloper.sec01.Assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import reactor.core.publisher.Mono;

public class FilesService {
    private static final Path PATH = Paths.get("src/main/resources/assignment/sec01");

    private static String readFile(String filename) {
        try {
            var file = new File(PATH.toString() + "/" + filename);
            if (!file.exists())
                file.createNewFile();
            return Files.readString(PATH.resolve(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void writeFile(String filename, String data, boolean append) {
        try {
            var writer = new FileWriter(PATH.resolve(filename).toFile(), append);
            writer.append(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteFile(String filename) {
        try {
            Files.delete(PATH.resolve(filename));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Mono<String> read(String filename) {
        return Mono.fromCallable(() -> readFile(filename));
    }

    public static Mono<Void> write(String filename, String data, boolean append) {
        return Mono.fromRunnable(() -> FilesService.writeFile(filename, data, append));
    }

    public static Mono<Void> delete(String filename) {
        return Mono.fromRunnable(() -> deleteFile(filename));
    }
}
