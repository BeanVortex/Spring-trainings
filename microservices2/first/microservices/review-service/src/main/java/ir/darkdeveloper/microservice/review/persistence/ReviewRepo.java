package ir.darkdeveloper.microservice.review.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Repository
public interface ReviewRepo extends ReactiveCrudRepository<ReviewEntity, Integer> {

    @Transactional(readOnly = true)
    Flux<ReviewEntity> findByProductId(int productId);
}
