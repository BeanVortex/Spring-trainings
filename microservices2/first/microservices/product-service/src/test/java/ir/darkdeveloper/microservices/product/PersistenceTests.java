package ir.darkdeveloper.microservices.product;

import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ir.darkdeveloper.microservices.product.persistence.ProductEntity;
import ir.darkdeveloper.microservices.product.persistence.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class PersistenceTests extends MongoDbTestBase {

  @Autowired
  private ProductRepo repo;

  private ProductEntity savedEntity;

  @BeforeEach
  void setupDb() {
    repo.deleteAll();

    ProductEntity entity = new ProductEntity(1, "n", 1);
    savedEntity = repo.save(entity);

    assertEqualsProduct(entity, savedEntity);
  }


  @Test
  void create() {

    ProductEntity newEntity = new ProductEntity(2, "n", 2);
    repo.save(newEntity);

    ProductEntity foundEntity = repo.findById(newEntity.getId()).get();
    assertEqualsProduct(newEntity, foundEntity);

    assertEquals(2, repo.count());
  }

  @Test
  void update() {
    savedEntity.setName("n2");
    repo.save(savedEntity);

    ProductEntity foundEntity = repo.findById(savedEntity.getId()).get();
    assertEquals(1, (long)foundEntity.getVersion());
    assertEquals("n2", foundEntity.getName());
  }

  @Test
  void delete() {
    repo.delete(savedEntity);
    assertFalse(repo.existsById(savedEntity.getId()));
  }

  @Test
  void getByProductId() {
    Optional<ProductEntity> entity = repo.findByProductId(savedEntity.getProductId());

    assertTrue(entity.isPresent());
    assertEqualsProduct(savedEntity, entity.get());
  }

  @Test
  void duplicateError() {
    assertThrows(DuplicateKeyException.class, () -> {
      ProductEntity entity = new ProductEntity(1, "n", 1);
      entity.setId(savedEntity.getId());
      repo.save(entity);
    });
  }

  @Test
  void optimisticLockError() {

    // Store the saved entity in two separate entity objects
    ProductEntity entity1 = repo.findById(savedEntity.getId()).get();
    ProductEntity entity2 = repo.findById(savedEntity.getId()).get();

    // Update the entity using the first entity object
    entity1.setName("n1");
    repo.save(entity1);

    // Update the entity using the second entity object.
    // This should fail since the second entity now holds an old version number, i.e. an Optimistic Lock Error
    assertThrows(OptimisticLockingFailureException.class, () -> {
      entity2.setName("n2");
      repo.save(entity2);
    }); 

    // Get the updated entity from the database and verify its new sate
    ProductEntity updatedEntity = repo.findById(savedEntity.getId()).get();
    assertEquals(1, (int)updatedEntity.getVersion());
    assertEquals("n1", updatedEntity.getName());
  }

  @Test
  void paging() {

    repo.deleteAll();

    List<ProductEntity> newProducts = rangeClosed(1001, 1010)
      .mapToObj(i -> new ProductEntity(i, "name " + i, i))
      .collect(Collectors.toList());
    repo.saveAll(newProducts);

    Pageable nextPage = PageRequest.of(0, 4, ASC, "productId");
    nextPage = testNextPage(nextPage, "[1001, 1002, 1003, 1004]", true);
    nextPage = testNextPage(nextPage, "[1005, 1006, 1007, 1008]", true);
  }

  private Pageable testNextPage(Pageable nextPage, String expectedProductIds, boolean expectsNextPage) {
    Page<ProductEntity> productPage = repo.findAll(nextPage);
    assertEquals(expectedProductIds, productPage.getContent().stream().map(ProductEntity::getProductId).collect(Collectors.toList()).toString());
    assertEquals(expectsNextPage, productPage.hasNext());
    return productPage.nextPageable();
  }

  private void assertEqualsProduct(ProductEntity expectedEntity, ProductEntity actualEntity) {
    assertEquals(expectedEntity.getId(),               actualEntity.getId());
    assertEquals(expectedEntity.getVersion(),          actualEntity.getVersion());
    assertEquals(expectedEntity.getProductId(),        actualEntity.getProductId());
    assertEquals(expectedEntity.getName(),           actualEntity.getName());
    assertEquals(expectedEntity.getWeight(),           actualEntity.getWeight());
  }
}
