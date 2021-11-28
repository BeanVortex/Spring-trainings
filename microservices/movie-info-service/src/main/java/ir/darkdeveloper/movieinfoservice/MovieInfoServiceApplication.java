package ir.darkdeveloper.movieinfoservice;

import ir.darkdeveloper.movieinfoservice.entity.Movie;
import ir.darkdeveloper.movieinfoservice.repo.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class MovieInfoServiceApplication {

    private final MovieRepo repo;

    public static void main(String[] args) {
        SpringApplication.run(MovieInfoServiceApplication.class, args);
    }

    @PostConstruct
    void saveMovies() {
        var movies = Arrays.asList(
                new Movie("fa"),
                new Movie("faasdf"),
                new Movie("faasdffas")
        );
        repo.saveAll(movies);
    }
}

