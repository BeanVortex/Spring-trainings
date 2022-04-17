package ir.darkdeveloper.composite.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservice.api.core.product.ProductService;
import ir.darkdeveloper.microservice.api.core.recommendation.Recommendation;
import ir.darkdeveloper.microservice.api.core.recommendation.RecommendationService;
import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.api.core.review.ReviewService;
import ir.darkdeveloper.microservice.api.exceptions.InvalidInputException;
import ir.darkdeveloper.microservice.api.exceptions.NotFoundException;
import ir.darkdeveloper.microservice.util.http.HttpErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class ProductComposition implements ProductService, RecommendationService, ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductComposition.class);
    private static final String HTTP_PREFIX = "http://";

    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;
    private final ObjectMapper mapper;
    private final WebClient webClient;


    @Autowired
    public ProductComposition(ObjectMapper mapper,
                              @Value("${app.product-service.host}") String productServiceHost,
                              @Value("${app.product-service.port}") Integer productServicePort,
                              @Value("${app.recommendation-service.host}") String recommendationServiceHost,
                              @Value("${app.recommendation-service.port}") Integer recommendationServicePort,
                              @Value("${app.review-service.host}") String reviewServiceHost,
                              @Value("${app.review-service.port}") Integer reviewServicePort,
                              WebClient webClient) {
        this.mapper = mapper;
        this.webClient = webClient;
        productServiceUrl = HTTP_PREFIX + productServiceHost + ":" + productServicePort + "/product/";
        recommendationServiceUrl = HTTP_PREFIX + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation?productId=";
        reviewServiceUrl = HTTP_PREFIX + reviewServiceHost + ":" + reviewServicePort + "/review?productId=";

    }


    @Override
    public Mono<Product> createProduct(Product body) {
        var url = productServiceUrl;
        LOG.debug("Will post a new product to URL: {}", url);

        return webClient.post()
                .uri(url)
                .body(body, Product.class)
                .exchangeToMono(res -> res.bodyToMono(Product.class))
                .onErrorMap(HttpClientErrorException.class, this::handleHttpClientException);
    }

    @Override
    public Mono<Product> getProduct(Integer productId) {
        var url = productServiceUrl + "/" + productId;
        LOG.debug("Will call the getProduct API on URL: {}", url);

        return webClient.get()
                .uri(url)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Product.class))
                .onErrorMap(HttpClientErrorException.class, this::handleHttpClientException);
    }

    @Override
    public Mono<Void> deleteProduct(Integer productId) {
        String url = productServiceUrl + "/" + productId;
        LOG.debug("Will call the deleteProduct API on URL: {}", url);
        return webClient.delete()
                .uri(url)
                .exchangeToMono(res -> res.bodyToMono(Void.class))
                .onErrorMap(HttpClientErrorException.class, this::handleHttpClientException);

    }


    @Override
    public Mono<Recommendation> createRecommendation(Recommendation body) {
        return null;
//        try {
//            var url = recommendationServiceUrl;
//            LOG.debug("Will post a new recommendation to URL: {}", url);
//
//            var recommendation = restTemplate.postForObject(url, body, Recommendation.class);
//            if (recommendation != null)
//                LOG.debug("Created a recommendation with id: {}", recommendation.productId());
//            else
//                LOG.warn("null recommendation");
//
//            return recommendation;
//
//        } catch (HttpClientErrorException ex) {
//            throw handleHttpClientException(ex);
//        }
    }

    @Override
    public Flux<Recommendation> getRecommendations(Integer productId) {
        return null;
//
//        try {
//            String url = recommendationServiceUrl + "?productId=" + productId;
//
//            LOG.debug("Will call the getRecommendations API on URL: {}", url);
//            var recommendations = restTemplate
//                    .exchange(url, GET, null, new ParameterizedTypeReference<List<Recommendation>>() {
//                    })
//                    .getBody();
//
//            if (recommendations != null)
//                LOG.debug("Found {} recommendations for a product with id: {}", recommendations.size(), productId);
//            else
//                LOG.warn("null recommendations");
//
//            return recommendations;
//
//        } catch (Exception ex) {
//            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
//            return new ArrayList<>();
//        }
    }

    @Override
    public Mono<Void> deleteRecommendations(Integer productId) {
        return null;
//        try {
//            var url = recommendationServiceUrl + "?productId=" + productId;
//            LOG.debug("Will call the deleteRecommendations API on URL: {}", url);
//
//            return restTemplate.delete(url, );
//
//        } catch (HttpClientErrorException ex) {
//            throw handleHttpClientException(ex);
//        }
    }

    @Override
    public Mono<Review> createReview(Review body) {
        return null;
//        try {
//            var url = reviewServiceUrl;
//            LOG.debug("Will post a new review to URL: {}", url);
//
//            var review = restTemplate.exchange(url, POST, body,
//                    new ParameterizedTypeReference<Review>() {
//                    });
//            if (review != null)
//                LOG.debug("Created a review with id: {}", review.productId());
//            else
//                LOG.warn("null review");
//            return review;
//
//        } catch (HttpClientErrorException ex) {
//            throw handleHttpClientException(ex);
//        }
    }

    @Override
    public Flux<Review> getReviews(Integer productId) {

//        try {
//            var url = reviewServiceUrl + "?productId=" + productId;
//
//            LOG.debug("Will call the getReviews API on URL: {}", url);
//            var reviews = restTemplate
//                    .exchange(url, GET, null, new ParameterizedTypeReference<List<Review>>() {
//                    })
//                    .getBody();
//
//            if (reviews != null)
//                LOG.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
//            else
//                LOG.warn("null reviews");
//
//            return reviews;
//
//        } catch (Exception ex) {
//            LOG.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
//            return new ArrayList<>();
//        }
        return null;
    }

    @Override
    public Mono<Void> deleteReviews(Integer productId) {
//        try {
//            var url = reviewServiceUrl + "?productId=" + productId;
//            LOG.debug("Will call the deleteReviews API on URL: {}", url);
//
//            restTemplate.delete(url);
//
//        } catch (HttpClientErrorException ex) {
//            throw handleHttpClientException(ex);
//        }
        return null;
    }


    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        switch (ex.getStatusCode()) {

            case NOT_FOUND:
                return new NotFoundException(getErrorMessage(ex));

            case UNPROCESSABLE_ENTITY:
                return new InvalidInputException(getErrorMessage(ex));

            default:
                LOG.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                return ex;
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioEx) {
            return ex.getMessage();
        }
    }
}
