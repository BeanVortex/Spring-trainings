package ir.darkdeveloper.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import ir.darkdeveloper.moviecatalogservice.model.Rating;
import ir.darkdeveloper.moviecatalogservice.model.UserRating;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRatingInfo {

    private final RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
    commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
    })
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratings/user/" + userId, UserRating.class);
    }


    public UserRating getFallbackUserRating(String userId) {
        return new UserRating(List.of(new Rating("-1", -10)));
    }

}

