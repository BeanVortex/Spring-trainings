package ir.darkdeveloper.microservice.recommendation.services;

import ir.darkdeveloper.microservice.api.core.recommendation.Recommendation;
import ir.darkdeveloper.microservice.api.core.recommendation.RecommendationService;
import ir.darkdeveloper.microservice.api.exceptions.InvalidInputException;
import ir.darkdeveloper.microservice.recommendation.persistence.RecommendationEntity;
import ir.darkdeveloper.microservice.recommendation.persistence.RecommendationRepo;
import ir.darkdeveloper.microservice.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationRepo repo;

    private final RecommendationMapper mapper;

    private final ServiceUtil serviceUtil;


    @Override
    public Recommendation createRecommendation(Recommendation body) {
        try {
            RecommendationEntity entity = mapper.apiToEntity(body);
            RecommendationEntity newEntity = repo.save(entity);

            LOG.debug("createRecommendation: created a recommendation entity: {}/{}", body.productId(),
                    body.recommendationId());
            return mapper.entityToApi(newEntity);

        } catch (DuplicateKeyException dke) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.productId()
                    + ", Recommendation Id:" + body.recommendationId());
        }
    }


    @Override
    public List<Recommendation> getRecommendations(Integer productId) {

        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        var entityList = repo.findByProductId(productId);
        var list = mapper.entityListToApiList(entityList);
        var newList = list.stream().map(recommendation ->
                new Recommendation(recommendation.productId(),
                        recommendation.recommendationId(), recommendation.author(),
                        recommendation.rate(), recommendation.content(),
                        serviceUtil.getServiceAddress())
        ).toList();

        LOG.debug("getRecommendations: response size: {}", list.size());

        return newList;
    }

    @Override
    public void deleteRecommendations(Integer productId) {
        LOG.debug("deleteRecommendations: tries to delete recommendations for the product with productId: {}", productId);
        repo.deleteAll(repo.findByProductId(productId));
    }
}
