package ir.darkdeveloper.microservice.recommendation.services;

import ir.darkdeveloper.microservice.api.core.recommendation.Recommendation;
import ir.darkdeveloper.microservice.api.core.recommendation.RecommendationService;
import ir.darkdeveloper.microservice.api.exceptions.InvalidInputException;
import ir.darkdeveloper.microservice.recommendation.persistence.RecommendationRepo;
import ir.darkdeveloper.microservice.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Level;


@RestController
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationRepo repo;

    private final RecommendationMapper mapper;

    private final ServiceUtil serviceUtil;


    @Override
    public Mono<Recommendation> createRecommendation(Recommendation body) {
        if (body.productId() < 1)
            throw new InvalidInputException("Invalid productId: " + body.productId());

        var entity = mapper.apiToEntity(body);
        return repo.save(entity)
                .log(LOG.getName(), Level.FINE)
                .onErrorMap(
                        DuplicateKeyException.class,
                        ex -> new InvalidInputException("Duplicate key, Product Id: " + body.productId())
                )
                .map(mapper::entityToApi);
    }


    @Override
    public Flux<Recommendation> getRecommendations(Integer productId) {
        if (productId < 1)
            throw new InvalidInputException("Invalid productId: " + productId);

        LOG.info("Will get recommendations for product with id={}", productId);
        return repo.findByProductId(productId)
                .log(LOG.getName(), Level.FINE)
                .map(mapper::entityToApi)
                .map(r -> new Recommendation(r.productId(), r.recommendationId(), r.author(),
                        r.rate(), r.content(), serviceUtil.getServiceAddress()));
    }

    @Override
    public Mono<Void> deleteRecommendations(Integer productId) {
        if (productId < 1)
            throw new InvalidInputException("Invalid productId: " + productId);
        LOG.debug("deleteRecommendations: tries to delete recommendations for the product with productId: {}", productId);
        return repo.deleteAll(repo.findByProductId(productId));
    }
}
