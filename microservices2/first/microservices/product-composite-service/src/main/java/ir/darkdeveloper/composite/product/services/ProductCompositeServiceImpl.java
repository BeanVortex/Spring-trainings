package ir.darkdeveloper.composite.product.services;

import ir.darkdeveloper.microservice.api.composite.product.*;
import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservice.api.core.recommendation.Recommendation;
import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.api.exceptions.NotFoundException;
import ir.darkdeveloper.microservice.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductCompositeServiceImpl implements ProductCompositeService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeServiceImpl.class);

    private final ServiceUtil serviceUtil;
    private final ProductComposition composition;

    @Autowired
    public ProductCompositeServiceImpl(ServiceUtil serviceUtil, ProductComposition composition) {
        this.serviceUtil = serviceUtil;
        this.composition = composition;
    }

    @Override
    public void createProduct(ProductAggregate body) {

        try {

            LOG.debug("createCompositeProduct: creates a new composite entity for productId: {}", body.productId());

            var product = new Product(body.productId(), body.name(), body.weight(), null);
            composition.createProduct(product);

            if (body.recommendations() != null) {
                body.recommendations().forEach(r -> {
                    var recommendation = new Recommendation(body.productId(), r.recommendationId(), r.author(),
                            r.rate(), r.content(), null);
                    composition.createRecommendation(recommendation);
                });
            }

            if (body.reviews() != null) {
                body.reviews().forEach(r -> {
                    Review review = new Review(body.productId(), r.reviewId(), r.author(), r.subject(), r.content(),
                            null);
                    composition.createReview(review);
                });
            }

            LOG.debug("createCompositeProduct: composite entities created for productId: {}", body.productId());

        } catch (RuntimeException re) {
            LOG.warn("createCompositeProduct failed", re);
            throw re;
        }
    }


    @Override
    public ProductAggregate getProduct(Integer productId) {
        var product = composition.getProduct(productId);
        if (product == null)
            throw new NotFoundException("No product found for productId: " + productId);
        var recommendations = composition.getRecommendations(productId);
        var reviews = composition.getReviews(productId);
//        return createProductAggregate(product, recommendations, reviews, serviceUtil.getServiceAddress());
        return null;
    }


    @Override
    public void deleteProduct(Integer productId) {

        LOG.debug("deleteCompositeProduct: Deletes a product aggregate for productId: {}", productId);

        composition.deleteProduct(productId);

        composition.deleteRecommendations(productId);

        composition.deleteReviews(productId);

        LOG.debug("deleteCompositeProduct: aggregate entities deleted for productId: {}", productId);
    }


    private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendations,
                                                    List<Review> reviews, String serviceAddress) {

        // 1. Setup product info
        var productId = product.productId();
        var name = product.name();
        var weight = product.weight();

        // 2. Copy summary recommendation info, if available
        var recommendationSummaries = (recommendations == null) ? null :
                recommendations.stream()
                        .map(r -> new RecommendationSummary(r.recommendationId(), r.author(), r.rate(), r.content()))
                        .toList();

        // 3. Copy summary review info, if available
        var reviewSummaries = (reviews == null) ? null :
                reviews.stream()
                        .map(r -> new ReviewSummary(r.reviewId(), r.author(), r.subject(), r.content()))
                        .collect(Collectors.toList());

        // 4. Create info regarding the involved microservices addresses
        var productAddress = product.serviceAddress();
        var reviewAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).serviceAddress() : "";
        var recommendationAddress = (recommendations != null && recommendations.size() > 0)
                ? recommendations.get(0).serviceAddress() : "";
        var serviceAddresses = new ServiceAddresses(serviceAddress, productAddress, reviewAddress, recommendationAddress);

        return new ProductAggregate(productId, name, weight, recommendationSummaries, reviewSummaries, serviceAddresses);

    }


}
