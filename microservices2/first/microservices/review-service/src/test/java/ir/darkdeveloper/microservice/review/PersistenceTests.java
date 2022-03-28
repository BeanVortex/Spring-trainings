package ir.darkdeveloper.microservice.review;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;


import ir.darkdeveloper.microservice.review.persistence.ReviewEntity;
import ir.darkdeveloper.microservice.review.persistence.ReviewRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

@DataR2dbcTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersistenceTests {

    // run sql. r2dbc does not support auto table creation

    @Autowired
    private ReviewRepo repository;

    private ReviewEntity savedEntity;

    @BeforeEach
    void setupDb() {
        repository.deleteAll().block();

        var entity = new ReviewEntity(1, 2, "a", "s", "c");
        savedEntity = repository.save(entity).block();
        assertNotNull(savedEntity);

        assertEqualsReview(entity, savedEntity);
    }


    @Test
    void create() {

        var newEntity = new ReviewEntity(1, 3, "a", "s", "c");
        repository.save(newEntity).block();

        var foundEntity = repository.findById(newEntity.getId()).block();

        assertNotNull(foundEntity);
        assertEqualsReview(newEntity, foundEntity);

        assertEquals(2, repository.count().block());
    }

    @Test
    void update() {
        savedEntity.setAuthor("a2");
        repository.save(savedEntity).block();

        var foundEntity = repository.findById(savedEntity.getId()).block();
        assertNotNull(foundEntity);
        assertEquals(1, (long) foundEntity.getVersion());
        assertEquals("a2", foundEntity.getAuthor());
    }

    @Test
    void delete() {
        repository.delete(savedEntity).block();
        var doesExists = repository.existsById(savedEntity.getId()).block();
        assertNotNull(doesExists);
        assertFalse(doesExists);
    }

    @Test
    void getByProductId() {
        var entityList = repository.findByProductId(savedEntity.getProductId()).collectList().block();

        assertThat(entityList, hasSize(1));
        assertEqualsReview(savedEntity, entityList.get(0));
    }

    @Test
    void duplicateError() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            var entity = new ReviewEntity(1, 2, "a", "s", "c");
            entity.setId(savedEntity.getId());
            repository.save(entity).block();
        });

    }

    @Test
    void optimisticLockError() {

        // Store the saved entity in two separate entity objects
        var entity1 = repository.findById(savedEntity.getId()).block();
        var entity2 = repository.findById(savedEntity.getId()).block();

        assertNotNull(entity1);
        assertNotNull(entity2);

        // Update the entity using the first entity object
        entity1.setAuthor("a1");
        repository.save(entity1).block();

        // Update the entity using the second entity object.
        // This should fail since the second entity now holds an old version number, i.e. an Optimistic Lock Error
        assertThrows(OptimisticLockingFailureException.class, () -> {
            entity2.setAuthor("a2");
            repository.save(entity2).block();
        });

        // Get the updated entity from the database and verify its new state
        var updatedEntity = repository.findById(savedEntity.getId()).block();
        assertNotNull(updatedEntity);
        assertEquals(1, (int) updatedEntity.getVersion());
        assertEquals("a1", updatedEntity.getAuthor());
    }

    private void assertEqualsReview(ReviewEntity expectedEntity, ReviewEntity actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntity.getProductId(), actualEntity.getProductId());
        assertEquals(expectedEntity.getReviewId(), actualEntity.getReviewId());
        assertEquals(expectedEntity.getAuthor(), actualEntity.getAuthor());
        assertEquals(expectedEntity.getSubject(), actualEntity.getSubject());
        assertEquals(expectedEntity.getContent(), actualEntity.getContent());
    }
}