package ir.darkdeveloper.microservice.review;

import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.api.core.review.ReviewService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class ReviewServiceApplication implements ReviewService {

    public static void main(String[] args) {
        SpringApplication.run(ReviewServiceApplication.class, args);
    }

    @Override
    public List<Review> getReviews(int productId) {
        return List.of(new Review(productId, 12, "adsf", "asdf", "sadf", "adf"));
    }
}
