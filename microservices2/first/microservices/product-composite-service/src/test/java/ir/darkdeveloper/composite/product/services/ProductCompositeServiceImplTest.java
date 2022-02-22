package ir.darkdeveloper.composite.product.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
record ProductCompositeServiceImplTest(WebTestClient client) {

    private static final Integer productId = 5;

    @Autowired
    public ProductCompositeServiceImplTest {
    }

    @Test
    @Order(1)
    @Disabled
    void getProduct() {
        // other microservices should be available(online and running)
        client.get()
                .uri("/product-composite/" + productId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(productId)
                .jsonPath("$.recommendations.length()").isEqualTo(1)
                .jsonPath("$.reviews.length()").isEqualTo(1);
    }
}