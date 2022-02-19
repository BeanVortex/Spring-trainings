package ir.darkdeveloper.microservice.api.core.product;

public record Product(Integer productId,
        String name,
        Integer weight,
        String serviceAddress) {
            
}
