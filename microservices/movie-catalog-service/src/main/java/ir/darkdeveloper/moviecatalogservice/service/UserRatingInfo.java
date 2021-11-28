package ir.darkdeveloper.moviecatalogservice.service;

import ir.darkdeveloper.moviecatalogservice.model.Rating;
import ir.darkdeveloper.moviecatalogservice.model.UserRating;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRatingInfo {

    private final CircuitBreakerFactory breakerFactory;
    private final RestTemplate restTemplate;


    public UserRating getUserRating(String userId) {
        var circuitBreaker = breakerFactory.create("userRatingBreaker");
        var url = "http://ratings-data-service/ratings/user/" + userId;

        return circuitBreaker.run(
                () -> restTemplate.getForObject(url, UserRating.class),
                throwable -> new UserRating(List.of(new Rating("-1", -10)))
        );
    }


}

