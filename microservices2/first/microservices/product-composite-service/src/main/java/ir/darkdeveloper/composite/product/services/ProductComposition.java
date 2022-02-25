package ir.darkdeveloper.composite.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservice.api.core.product.ProductService;
import ir.darkdeveloper.microservice.api.core.recommendation.Recommendation;
import ir.darkdeveloper.microservice.api.core.recommendation.RecommendationService;
import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.api.core.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ProductComposition implements ProductService, RecommendationService, ReviewService {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    @Autowired
    public ProductComposition(RestTemplate restTemplate,
                              @Value("${app.product-service.host}") String productServiceHost,
                              @Value("${app.product-service.port}") Integer productServicePort,
                              @Value("${app.recommendation-service.host}") String recommendationServiceHost,
                              @Value("${app.recommendation-service.port}") Integer recommendationServicePort,
                              @Value("${app.review-service.host}") String reviewServiceHost,
                              @Value("${app.review-service.port}") Integer reviewServicePort) {
        this.restTemplate = restTemplate;
        productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product/";
        recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation?productId=";
        reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review?productId=";

    }


    @Override
    public Product getProduct(Integer productId) {
        var url = productServiceUrl + productId;
        return restTemplate.getForObject(url, Product.class);
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        var url = recommendationServiceUrl + productId;
       return restTemplate.exchange(url, HttpMethod.GET, null,
                       new ParameterizedTypeReference<List<Recommendation>>() {
                       })
               .getBody();
    }

    @Override
    public List<Review> getReviews(int productId) {
        var url = reviewServiceUrl + productId;
       return restTemplate.exchange(url, HttpMethod.GET, null,
                       new ParameterizedTypeReference<List<Review>>() {
                       })
               .getBody();
    }
}
