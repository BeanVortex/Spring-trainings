package ir.darkdeveloper.microservice.review;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.review.persistence.ReviewEntity;
import ir.darkdeveloper.microservice.review.services.ReviewMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;


class MapperTests {

  private final ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);

  @Test
  void mapperTests() {

    assertNotNull(mapper);

    var api = new Review(1, 2, "a", "s", "C", "adr");

    var entity = mapper.apiToEntity(api);

    assertEquals(api.productId(), entity.getProductId());
    assertEquals(api.reviewId(), entity.getReviewId());
    assertEquals(api.author(), entity.getAuthor());
    assertEquals(api.subject(), entity.getSubject());
    assertEquals(api.content(), entity.getContent());

    var api2 = mapper.entityToApi(entity);

    assertEquals(api.productId(), api2.productId());
    assertEquals(api.reviewId(), api2.reviewId());
    assertEquals(api.author(), api2.author());
    assertEquals(api.subject(), api2.subject());
    assertEquals(api.content(), api2.content());
    assertNull(api2.serviceAddress());
  }

  @Test
  void mapperListTests() {

    assertNotNull(mapper);

    var api = new Review(1, 2, "a", "s", "C", "adr");
    var apiList = Collections.singletonList(api);

    var entityList = mapper.apiListToEntityList(apiList);
    assertEquals(apiList.size(), entityList.size());

    var entity = entityList.get(0);

    assertEquals(api.productId(), entity.getProductId());
    assertEquals(api.reviewId(), entity.getReviewId());
    assertEquals(api.author(), entity.getAuthor());
    assertEquals(api.subject(), entity.getSubject());
    assertEquals(api.content(), entity.getContent());

    var api2List = mapper.entityListToApiList(entityList);
    assertEquals(apiList.size(), api2List.size());

    var api2 = api2List.get(0);

    assertEquals(api.productId(), api2.productId());
    assertEquals(api.reviewId(), api2.reviewId());
    assertEquals(api.author(), api2.author());
    assertEquals(api.subject(), api2.subject());
    assertEquals(api.content(), api2.content());
    assertNull(api2.serviceAddress());
  }
}
