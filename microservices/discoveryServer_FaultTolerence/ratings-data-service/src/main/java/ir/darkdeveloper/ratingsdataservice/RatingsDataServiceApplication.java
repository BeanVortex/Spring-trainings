package ir.darkdeveloper.ratingsdataservice;

import ir.darkdeveloper.ratingsdataservice.entity.Rating;
import ir.darkdeveloper.ratingsdataservice.repo.RatingRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class RatingsDataServiceApplication {

    private final RatingRepo repo;

    public static void main(String[] args) {
        SpringApplication.run(RatingsDataServiceApplication.class, args);
    }

    @PostConstruct
    void saveRatings() {
        List<Rating> ratings = Arrays.asList(
                new Rating(0L,"48c2c502-5907-4a6a-afcb-9ba16639887a", 2),
                new Rating(0L,"6dfaf098-fdb6-4210-b548-67d9b47aff13", 5)
        );
        repo.saveAll(ratings);
    }
}

