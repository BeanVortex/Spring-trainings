package ir.darkdeveloper.composite.product.services;

import ir.darkdeveloper.microservice.api.composite.product.*;
import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservice.api.core.recommendation.Recommendation;
import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static java.util.logging.Level.FINE;

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
    public Mono<Void> createProduct(ProductAggregate body) {

        try {
            var monoList = new ArrayList<Mono>();

            LOG.debug("createCompositeProduct: creates a new composite entity for productId: {}", body.productId());

            var product = new Product(body.productId(), body.name(), body.weight(), null);
            monoList.add(composition.createProduct(product));

            if (body.recommendations() != null) {
                body.recommendations().forEach(r -> {
                    var recommendation = new Recommendation(body.productId(), r.recommendationId(), r.author(), r.rate(), r.content(), null);
                    monoList.add(composition.createRecommendation(recommendation));
                });
            }

            if (body.reviews() != null) {
                body.reviews().forEach(r -> {
                    Review review = new Review(body.productId(), r.reviewId(), r.author(), r.subject(), r.content(), null);
                    monoList.add(composition.createReview(review));
                });
            }

            LOG.debug("createCompositeProduct: composite entities created for productId: {}", body.productId());

            return Mono.zip(r -> "", monoList.toArray(new Mono[0]))
                    .doOnError(ex -> LOG.warn("createCompositeProduct failed: {}", ex.toString()))
                    .then();

        } catch (RuntimeException re) {
            LOG.warn("createCompositeProduct failed", re);
            throw re;
        }
    }


    @Override
    public Mono<ProductAggregate> getProduct(Integer productId) {
        return Mono.zip(
                        values -> createProductAggregate(
                                (Product) values[0],
                                (List<Recommendation>) values[1],
                                (List<Review>) values[2],
                                serviceUtil.getServiceAddress()
                        ),
                        composition.getProduct(productId),
                        composition.getRecommendations(productId).collectList(),
                        composition.getReviews(productId).collectList()
                ).doOnError(ex -> LOG.warn("getCompositeProduct failed: {}", ex.toString()))
                .log(LOG.getName(), FINE);

    }


    @Override
    public Mono<Void> deleteProduct(Integer productId) {

        try {

            LOG.debug("deleteCompositeProduct: Deletes a product aggregate for productId: {}", productId);

            return Mono.zip(
                            r -> "",
                            composition.deleteProduct(productId),
                            composition.deleteRecommendations(productId),
                            composition.deleteReviews(productId))
                    .doOnError(ex -> LOG.warn("delete failed: {}", ex.toString()))
                    .log(LOG.getName(), FINE).then();

        } catch (RuntimeException re) {
            LOG.warn("deleteCompositeProduct failed: {}", re.toString());
            throw re;
        }
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
