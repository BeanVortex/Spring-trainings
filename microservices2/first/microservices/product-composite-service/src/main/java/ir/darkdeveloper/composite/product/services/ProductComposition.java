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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class ProductComposition implements ProductService, RecommendationService, ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductComposition.class);


    private final RestTemplate restTemplate;
    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;
    private final ObjectMapper mapper;


    @Autowired
    public ProductComposition(RestTemplate restTemplate,
                              ObjectMapper mapper,
                              @Value("${app.product-service.host}") String productServiceHost,
                              @Value("${app.product-service.port}") Integer productServicePort,
                              @Value("${app.recommendation-service.host}") String recommendationServiceHost,
                              @Value("${app.recommendation-service.port}") Integer recommendationServicePort,
                              @Value("${app.review-service.host}") String reviewServiceHost,
                              @Value("${app.review-service.port}") Integer reviewServicePort) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product/";
        recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation?productId=";
        reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review?productId=";

    }


    @Override
    public Product createProduct(Product body) {

        try {
            var url = productServiceUrl;
            LOG.debug("Will post a new product to URL: {}", url);

            var product = restTemplate.postForObject(url, body, Product.class);
            if (product != null)
                LOG.debug("Created a product with id: {}", product.productId());
            else
                LOG.warn("null product");

            return product;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public Product getProduct(Integer productId) {

        try {
            var url = productServiceUrl + "/" + productId;
            LOG.debug("Will call the getProduct API on URL: {}", url);

            var product = restTemplate.getForObject(url, Product.class);
            if (product != null)
                LOG.debug("Created a product with id: {}", product.productId());
            else
                LOG.warn("null product");

            return product;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        try {
            String url = productServiceUrl + "/" + productId;
            LOG.debug("Will call the deleteProduct API on URL: {}", url);
            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    @Override
    public Recommendation createRecommendation(Recommendation body) {
        try {
            var url = recommendationServiceUrl;
            LOG.debug("Will post a new recommendation to URL: {}", url);

            var recommendation = restTemplate.postForObject(url, body, Recommendation.class);
            if (recommendation != null)
                LOG.debug("Created a recommendation with id: {}", recommendation.productId());
            else
                LOG.warn("null recommendation");

            return recommendation;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public List<Recommendation> getRecommendations(Integer productId) {


        try {
            String url = recommendationServiceUrl + "?productId=" + productId;

            LOG.debug("Will call the getRecommendations API on URL: {}", url);
            List<Recommendation> recommendations = restTemplate
                    .exchange(url, GET, null, new ParameterizedTypeReference<List<Recommendation>>() {
                    })
                    .getBody();

            if (recommendations != null)
                LOG.debug("Found {} recommendations for a product with id: {}", recommendations.size(), productId);
            else
                LOG.warn("null recommendations");

            return recommendations;

        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteRecommendations(Integer productId) {
        try {
            String url = recommendationServiceUrl + "?productId=" + productId;
            LOG.debug("Will call the deleteRecommendations API on URL: {}", url);

            restTemplate.delete(url);

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public Review createReview(Review body) {

        try {
            String url = reviewServiceUrl;
            LOG.debug("Will post a new review to URL: {}", url);

            Review review = restTemplate.postForObject(url, body, Review.class);
            if (review != null)
                LOG.debug("Created a review with id: {}", review.productId());
            else
                LOG.warn("null review");
            return review;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    @Override
    public List<Review> getReviews(Integer productId) {

        try {
            String url = reviewServiceUrl + "?productId=" + productId;

            LOG.debug("Will call the getReviews API on URL: {}", url);
            List<Review> reviews = restTemplate
                    .exchange(url, GET, null, new ParameterizedTypeReference<List<Review>>() {
                    })
                    .getBody();

            if (reviews != null)
                LOG.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
            else
                LOG.warn("null reviews");

            return reviews;

        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteReviews(Integer productId) {
        try {
            String url = reviewServiceUrl + "?productId=" + productId;
            LOG.debug("Will call the deleteReviews API on URL: {}", url);

            restTemplate.delete(url);

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
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
