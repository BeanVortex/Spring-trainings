package ir.darkdeveloper.microservice.api.composite.product;

import org.springframework.web.bind.annotation.*;

public interface ProductCompositeService {


    /**
     * Sample usage, see below.
     *
     * curl -X POST $HOST:$PORT/product-composite \
     *   -H "Content-Type: application/json" --data \
     *   '{"productId":123,"name":"product 123","weight":123}'
     *
     * @param body A JSON representation of the new composite product
     */
    @PostMapping(
            value    = "/product-composite",
            consumes = "application/json")
    void createProduct(@RequestBody ProductAggregate body);

    /**
     * Sample usage: "curl $HOST:$PORT/product-composite/1".
     *
     * @param productId Id of the product
     * @return the composite product info, if found, else null
     */
    @GetMapping(
            value = "/product-composite/{productId}",
            produces = "application/json")
    ProductAggregate getProduct(@PathVariable Integer productId);

    /**
     * Sample usage: "curl -X DELETE $HOST:$PORT/product-composite/1".
     *
     * @param productId Id of the product
     */
    @DeleteMapping(value = "/product-composite/{productId}")
    void deleteProduct(@PathVariable Integer productId);
}
