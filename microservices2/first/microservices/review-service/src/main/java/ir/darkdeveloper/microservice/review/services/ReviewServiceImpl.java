package ir.darkdeveloper.microservice.review.services;

import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.api.core.review.ReviewService;
import ir.darkdeveloper.microservice.api.exceptions.InvalidInputException;
import ir.darkdeveloper.microservice.review.persistence.ReviewRepo;
import ir.darkdeveloper.microservice.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepo repo;
    private final ReviewMapper mapper;
    private final ServiceUtil serviceUtil;

    @Autowired
    public ReviewServiceImpl(ReviewRepo repo, ReviewMapper mapper, ServiceUtil serviceUtil) {
        this.repo = repo;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Mono<Review> createReview(Review body) {
        var entity = mapper.apiToEntity(body);
        return repo.save(entity)
                .onErrorMap(
                        DataIntegrityViolationException.class,
                        ex -> new InvalidInputException("Duplicate key, Product Id: "
                                + body.productId() + ", Review Id:" +
                                body.reviewId())
                )
                .map(mapper::entityToApi);
    }

    @Override
    public Flux<Review> getReviews(Integer productId) {
        if (productId < 1)
            throw new InvalidInputException("Invalid productId: " + productId);

        LOG.debug("getReviews: getting reviews with id of :{}", productId);
        return repo.findByProductId(productId).map(mapper::entityToApi)
                .map(r -> new Review(r.productId(), r.reviewId(), r.author(),
                        r.subject(), r.content(), serviceUtil.getServiceAddress()));
    }

    @Override
    public Mono<Void> deleteReviews(Integer productId) {
        LOG.debug("deleteReviews: tries to delete reviews for the product with productId: {}", productId);
        return repo.deleteAll(repo.findByProductId(productId));
    }
}