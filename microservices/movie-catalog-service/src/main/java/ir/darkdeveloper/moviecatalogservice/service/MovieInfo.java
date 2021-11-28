package ir.darkdeveloper.moviecatalogservice.service;

import ir.darkdeveloper.moviecatalogservice.model.CatalogItem;
import ir.darkdeveloper.moviecatalogservice.model.Movie;
import ir.darkdeveloper.moviecatalogservice.model.Rating;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class MovieInfo {

    private final CircuitBreakerFactory breakerFactory;
    private final RestTemplate restTemplate;


    public CatalogItem getCatalogItem(Rating rating) {
        var circuitBreaker = breakerFactory.create("movieCatalogBreaker");
        var url = "http://movie-info-service/movies/" + rating.getMovieId();
        return circuitBreaker.run(
                () -> {
                    var movie = restTemplate.getForObject(url, Movie.class);
                    return new CatalogItem(movie.getName(), "desc", rating.getRating());
                },
                throwable ->
                        new CatalogItem("Movie name not found", "", rating.getRating())
        );
    }

}
