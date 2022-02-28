package ir.darkdeveloper.microservice.api.core.review;


public record Review(Integer productId, Integer reviewId, String author,
                     String subject, String content, String serviceAddress) {
}
