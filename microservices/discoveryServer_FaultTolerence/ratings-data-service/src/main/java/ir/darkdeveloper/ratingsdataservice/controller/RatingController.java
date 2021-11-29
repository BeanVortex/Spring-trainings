package ir.darkdeveloper.ratingsdataservice.controller;

import ir.darkdeveloper.ratingsdataservice.entity.Rating;
import ir.darkdeveloper.ratingsdataservice.model.UserRating;
import ir.darkdeveloper.ratingsdataservice.repo.RatingRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ratings")
@AllArgsConstructor
public class RatingController {

    private final RatingRepo repo;

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return repo.findById(movieId).orElse(null);
    }

    @GetMapping("/user/{userId}")
    public UserRating getRatings(@PathVariable String userId) {
        return new UserRating(repo.findAll());
    }
}
