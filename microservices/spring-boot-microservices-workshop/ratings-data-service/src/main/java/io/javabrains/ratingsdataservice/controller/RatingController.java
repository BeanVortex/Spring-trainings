package io.javabrains.ratingsdataservice.controller;

import io.javabrains.ratingsdataservice.model.Rating;
import io.javabrains.ratingsdataservice.model.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return new Rating(movieId, 5);
    }

    @GetMapping("/user/{userId}")
    public UserRating getRatings(@PathVariable String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("1", 2),
                new Rating("2", 5)
        );
        return new UserRating(ratings);
    }
}
