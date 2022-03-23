package ir.darkdeveloper.microservice.review.services;

import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.api.core.review.ReviewService;
import ir.darkdeveloper.microservice.api.exceptions.InvalidInputException;
import ir.darkdeveloper.microservice.review.persistence.ReviewEntity;
import ir.darkdeveloper.microservice.review.persistence.ReviewRepo;
import ir.darkdeveloper.microservice.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Review createReview(Review body) {
        try {
            ReviewEntity entity = mapper.apiToEntity(body);
            ReviewEntity newEntity = repo.save(entity);

            LOG.debug("createReview: created a review entity: {}/{}", body.productId(), body.reviewId());
            return mapper.entityToApi(newEntity);

        } catch (DataIntegrityViolationException dive) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.productId() + ", Review Id:" +
                    body.reviewId());
        }
    }

    @Override
    public List<Review> getReviews(Integer productId) {

        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        var entityList = repo.findByProductId(productId);
        var list = mapper.entityListToApiList(entityList);
        var newList = list.stream()
                .map(review -> new Review(review.productId(), review.reviewId(), review.author(),
                        review.subject(), review.content(), serviceUtil.getServiceAddress()))
                .toList();

        LOG.debug("getReviews: response size: {}", newList.size());

        return newList;
    }

    @Override
    public void deleteReviews(Integer productId) {
        LOG.debug("deleteReviews: tries to delete reviews for the product with productId: {}", productId);
        repo.deleteAll(repo.findByProductId(productId));
    }
}