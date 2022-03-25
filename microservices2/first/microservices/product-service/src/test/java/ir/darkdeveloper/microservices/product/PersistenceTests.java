package ir.darkdeveloper.microservices.product;

import ir.darkdeveloper.microservices.product.persistence.ProductEntity;
import ir.darkdeveloper.microservices.product.persistence.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class PersistenceTests extends MongoDbTestBase {

    @Autowired
    private ProductRepo repo;

    private ProductEntity savedEntity;

    @BeforeEach
    void setupDb() {
        repo.deleteAll().block();

        var entity = new ProductEntity(1, "n", 1);
        savedEntity = repo.save(entity).block();
        assertNotNull(savedEntity);
        assertEqualsProduct(entity, savedEntity);
    }


    @Test
    void create() {

        var newEntity = new ProductEntity(2, "n", 2);
        repo.save(newEntity).block();

        var foundEntity = repo.findById(newEntity.getId()).block();

        assertNotNull(foundEntity);
        assertEqualsProduct(newEntity, foundEntity);
        assertEquals(2, repo.count().block());
    }

    @Test
    void update() {
        savedEntity.setName("n2");
        repo.save(savedEntity).block();

        var foundEntity = repo.findById(savedEntity.getId()).block();
        assertNotNull(foundEntity);
        assertEquals(1, (long) foundEntity.getVersion());
        assertEquals("n2", foundEntity.getName());
    }

    @Test
    void delete() {
        repo.delete(savedEntity).block();
        var res = repo.existsById(savedEntity.getId()).block();
        assertNotNull(res);
        assertFalse(res);
    }

    @Test
    void getByProductId() {
        var entity = repo.findByProductId(savedEntity.getProductId()).block();

        assertNotNull(entity);
        assertEqualsProduct(savedEntity, entity);
    }

    @Test
    void duplicateError() {
        assertThrows(DuplicateKeyException.class, () -> {
            var entity = new ProductEntity(1, "n", 1);
            entity.setId(savedEntity.getId());
            repo.save(entity).block();
        });
    }

    @Test
    void optimisticLockError() {

        // Store the saved entity in two separate entity objects
        var entity1 = repo.findById(savedEntity.getId()).block();
        var entity2 = repo.findById(savedEntity.getId()).block();

        assertNotNull(entity1);
        assertNotNull(entity2);

        // Update the entity using the first entity object
        entity1.setName("n1");
        repo.save(entity1).block();

        // Update the entity using the second entity object.
        // This should fail since the second entity now holds an old version number, i.e. an Optimistic Lock Error
        assertThrows(OptimisticLockingFailureException.class, () -> {
            entity2.setName("n2");
            repo.save(entity2).block();
        });

        // Get the updated entity from the database and verify its new sate
        var updatedEntity = repo.findById(savedEntity.getId()).block();
        assertNotNull(updatedEntity);
        assertEquals(1, (int) updatedEntity.getVersion());
        assertEquals("n1", updatedEntity.getName());
    }


    private void assertEqualsProduct(ProductEntity expectedEntity, ProductEntity actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntity.getProductId(), actualEntity.getProductId());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
        assertEquals(expectedEntity.getWeight(), actualEntity.getWeight());
    }
}
