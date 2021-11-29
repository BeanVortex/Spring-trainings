package ir.darkdeveloper.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import ir.darkdeveloper.moviecatalogservice.model.CatalogItem;
import ir.darkdeveloper.moviecatalogservice.model.Movie;
import ir.darkdeveloper.moviecatalogservice.model.Rating;
import ir.darkdeveloper.moviecatalogservice.model.UserRating;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class MovieInfo {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        var movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),
                Movie.class);
        assert movie != null;
        return new CatalogItem(movie.getName(), "desc", rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }
}
