package ir.darkdeveloper.microservices.product.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface ProductRepo extends ReactiveCrudRepository<ProductEntity, String> {

    Mono<ProductEntity> findByProductId(int productId);
}
