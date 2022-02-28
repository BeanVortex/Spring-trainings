package ir.darkdeveloper.microservice.recommendation.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendationRepo extends CrudRepository<RecommendationEntity, String> {
    List<RecommendationEntity> findByProductId(int productId);
}
