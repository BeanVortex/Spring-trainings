package ir.darkdeveloper.composite.product.services;

import ir.darkdeveloper.microservice.api.composite.product.ProductAggregate;
import ir.darkdeveloper.microservice.api.composite.product.ProductCompositeService;
import ir.darkdeveloper.microservice.api.composite.product.RecommendationSummary;
import ir.darkdeveloper.microservice.api.composite.product.ReviewSummary;
import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservice.api.core.recommendation.Recommendation;
import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductCompositeServiceImpl implements ProductCompositeService {

    private final ServiceUtil serviceUtil;
    private final ProductComposition composition;

    @Autowired
    public ProductCompositeServiceImpl(ServiceUtil serviceUtil, ProductComposition composition) {
        this.serviceUtil = serviceUtil;
        this.composition = composition;
    }

    @Override
    public ProductAggregate getProduct(Integer productId) {
        var product = composition.getProduct(productId);
        var recommendations = composition.getRecommendations(productId);
        var reviews = composition.getReviews(productId);
        return createProductAggregate(product, recommendations, reviews, serviceUtil.getServiceAddress());
    }

    private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendations,
                                                    List<Review> reviews, String serviceAddress) {
        var recSum = recommendations.stream()
                .map(rec -> new RecommendationSummary(rec.recommendationId(), rec.author(), rec.rate()))
                .toList();
        var revSum = reviews.stream()
                .map(rev -> new ReviewSummary(rev.reviewId(), rev.author(), rev.subject()))
                .toList();
        return new ProductAggregate(product.productId(), product.name(), product.weight(), recSum,
                revSum, serviceAddress);
    }


}
