package ir.darkdeveloper.microservices.product.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductRepo extends PagingAndSortingRepository<ProductEntity, String> {

    Optional<ProductEntity> findByProductId(int productId);
}
