package ir.darkdeveloper.microservice.api.core.recommendation;

public record Recommendation(Integer productId,
                             Integer recommendationId,
                             String author,
                             Integer rate,
                             String content,
                             String serviceAddress) {

}
