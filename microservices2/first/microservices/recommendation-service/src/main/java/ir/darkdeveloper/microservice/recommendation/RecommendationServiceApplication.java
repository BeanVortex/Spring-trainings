package ir.darkdeveloper.microservice.recommendation;

import ir.darkdeveloper.microservice.api.core.recommendation.Recommendation;
import ir.darkdeveloper.microservice.api.core.recommendation.RecommendationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class RecommendationServiceApplication implements RecommendationService {

    public static void main(String[] args) {
        SpringApplication.run(RecommendationServiceApplication.class, args);
    }


    @Override
    public List<Recommendation> getRecommendations(int productId) {
        return List.of(new Recommendation(productId, 4,
                "adsf", 10, "content", "addr"));
    }
}
