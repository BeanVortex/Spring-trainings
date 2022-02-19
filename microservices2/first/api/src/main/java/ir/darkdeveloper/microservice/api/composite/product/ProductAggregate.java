package ir.darkdeveloper.microservice.api.composite.product;

import java.util.List;

public record ProductAggregate(Integer productId, String name, Integer weight,
                               List<RecommendationSummary> recommendations,
                               List<ReviewSummary> reviews,
                               String serviceAddresses) {
}
