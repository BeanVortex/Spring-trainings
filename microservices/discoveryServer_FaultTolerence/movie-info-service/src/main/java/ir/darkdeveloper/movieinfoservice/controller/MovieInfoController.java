package ir.darkdeveloper.movieinfoservice.controller;

import ir.darkdeveloper.movieinfoservice.entity.Movie;
import ir.darkdeveloper.movieinfoservice.repo.MovieRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieInfoController {

    private final MovieRepo repo;


    @GetMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable String movieId) throws InterruptedException {
//        System.out.println("dsa");
//        Thread.sleep(5000);
        return repo.findById(movieId).orElse(null);
    }
}
