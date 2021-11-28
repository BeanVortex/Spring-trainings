package ir.darkdeveloper.moviecatalogservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import ir.darkdeveloper.moviecatalogservice.model.CatalogItem;
import ir.darkdeveloper.moviecatalogservice.model.Movie;
import ir.darkdeveloper.moviecatalogservice.model.Rating;
import ir.darkdeveloper.moviecatalogservice.model.UserRating;
import ir.darkdeveloper.moviecatalogservice.service.MovieInfo;
import ir.darkdeveloper.moviecatalogservice.service.UserRatingInfo;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
@AllArgsConstructor
public class MovieCatalogController {

    //        Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://movie-info-service/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();

    private final WebClient.Builder webClientBuilder;
    private final DiscoveryClient discoveryClient;
    private final MovieInfo movieInfo;
    private final UserRatingInfo userRatingInfo;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        UserRating userRating = userRatingInfo.getUserRating(userId);
        return userRating.getUserRating().stream().map(movieInfo::getCatalogItem).collect(Collectors.toList());
    }


}
