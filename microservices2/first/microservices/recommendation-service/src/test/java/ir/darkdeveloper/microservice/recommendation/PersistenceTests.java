package ir.darkdeveloper.microservice.recommendation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;


import ir.darkdeveloper.microservice.recommendation.persistence.RecommendationEntity;
import ir.darkdeveloper.microservice.recommendation.persistence.RecommendationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.List;


@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class PersistenceTests extends MongoDbTestBase {

    private final RecommendationRepo repo;

    private RecommendationEntity savedEntity;

    @Autowired
    PersistenceTests(RecommendationRepo repo) {
        this.repo = repo;
    }

    @BeforeEach
    void setupDb() {
        repo.deleteAll().block();

        var entity = new RecommendationEntity(1, 2, "a", 3, "c");
        savedEntity = repo.save(entity).block();

        assertEqualsRecommendation(entity, savedEntity);
    }


    @Test
    void create() {

        var newEntity = new RecommendationEntity(1, 3, "a", 3, "c");
        repo.save(newEntity).block();

        var foundEntity = repo.findById(newEntity.getId()).block();
        assertEqualsRecommendation(newEntity, foundEntity);

        assertEquals(2L, repo.count().block());
    }

    @Test
    void update() {
        savedEntity.setAuthor("a2");
        repo.save(savedEntity).block();

        RecommendationEntity foundEntity = repo.findById(savedEntity.getId()).block();
        assertEquals(1, foundEntity.getVersion());
        assertEquals("a2", foundEntity.getAuthor());
    }

    @Test
    void delete() {
        repo.delete(savedEntity).block();
        assertFalse(repo.existsById(savedEntity.getId()).block());
    }

    @Test
    void getByProductId() {
        var entityList = repo.findByProductId(savedEntity.getProductId())
                .collectList().block();

        assertThat(entityList, hasSize(1));
        assertEqualsRecommendation(savedEntity, entityList.get(0));
    }

    @Test
    void duplicateError() {
        assertThrows(DuplicateKeyException.class, () -> {
            RecommendationEntity entity = new RecommendationEntity(1, 2, "a", 3, "c");
            entity.setId(savedEntity.getId());
            repo.save(entity).block();
        });
    }

    @Test
    void optimisticLockError() {

        // Store the saved entity in two separate entity objects
        var entity1 = repo.findById(savedEntity.getId()).block();
        var entity2 = repo.findById(savedEntity.getId()).block();

        // Update the entity using the first entity object
        entity1.setAuthor("a1");
        repo.save(entity1).block();

        //  Update the entity using the second entity object.
        // This should fail since the second entity now holds an old version number, i.e. an Optimistic Lock Error
        assertThrows(OptimisticLockingFailureException.class, () -> {
            entity2.setAuthor("a2");
            repo.save(entity2).block();
        });

        // Get the updated entity from the database and verify its new state
        var updatedEntity = repo.findById(savedEntity.getId()).block();
        assertEquals(1, (int) updatedEntity.getVersion());
        assertEquals("a1", updatedEntity.getAuthor());
    }

    private void assertEqualsRecommendation(RecommendationEntity expectedEntity, RecommendationEntity actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntity.getProductId(), actualEntity.getProductId());
        assertEquals(expectedEntity.getRecommendationId(), actualEntity.getRecommendationId());
        assertEquals(expectedEntity.getAuthor(), actualEntity.getAuthor());
        assertEquals(expectedEntity.getRating(), actualEntity.getRating());
        assertEquals(expectedEntity.getContent(), actualEntity.getContent());
    }
}
