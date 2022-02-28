package ir.darkdeveloper.microservice.review;

import java.util.List;

import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.review.persistence.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ReviewMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true),
            @Mapping(target = "productId", source = "productId")
    })
    Review entityToApi(ReviewEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    ReviewEntity apiToEntity(Review api);

    List<Review> entityListToApiList(List<ReviewEntity> entity);

    List<ReviewEntity> apiListToEntityList(List<Review> api);
}