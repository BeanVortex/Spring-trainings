package ir.darkdeveloper.composite.product.services;

import ir.darkdeveloper.microservice.api.composite.product.ProductAggregate;
import ir.darkdeveloper.microservice.api.composite.product.RecommendationSummary;
import ir.darkdeveloper.microservice.api.composite.product.ReviewSummary;
import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservice.api.core.recommendation.Recommendation;
import ir.darkdeveloper.microservice.api.core.review.Review;
import ir.darkdeveloper.microservice.api.exceptions.InvalidInputException;
import ir.darkdeveloper.microservice.api.exceptions.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT)
record ProductCompositeServiceImplTest(WebTestClient client) {

    private static final int PRODUCT_ID_OK = 1;
    private static final int PRODUCT_ID_NOT_FOUND = 2;
    private static final int PRODUCT_ID_INVALID = 3;

    @Autowired
    public ProductCompositeServiceImplTest {
    }

    @MockBean
    private static ProductComposition composition;

    @BeforeEach
    void setUp() {

        when(composition.getProduct(PRODUCT_ID_OK))
                .thenReturn(Mono.just(
                        new Product(PRODUCT_ID_OK, "name", 1, "mock-address")
                ));

        when(composition.getRecommendations(PRODUCT_ID_OK))
                .thenReturn(Flux.just(
                        new Recommendation(PRODUCT_ID_OK, 1, "author",
                                1, "content", "mock address"))
                );

        when(composition.getReviews(PRODUCT_ID_OK))
                .thenReturn(Flux.just(
                        new Review(PRODUCT_ID_OK, 1, "author", "subject",
                                "content", "mock address")
                ));

        when(composition.getProduct(PRODUCT_ID_NOT_FOUND))
                .thenThrow(new NotFoundException("NOT FOUND: " + PRODUCT_ID_NOT_FOUND));

        when(composition.getProduct(PRODUCT_ID_INVALID))
                .thenThrow(new InvalidInputException("INVALID: " + PRODUCT_ID_INVALID));
    }

    @Test
    void createCompositeProduct1() {
        var compositeProduct = new ProductAggregate(1, "name", 1, null,
                null, null);
        postAndVerifyProduct(compositeProduct);
    }

    @Test
    void createCompositeProduct2() {
        ProductAggregate compositeProduct = new ProductAggregate(1, "name", 1,
                singletonList(new RecommendationSummary(1, "a", 1, "c")),
                singletonList(new ReviewSummary(1, "a", "s", "c")), null);

        postAndVerifyProduct(compositeProduct);
    }

    @Test
    void deleteCompositeProduct() {
        ProductAggregate compositeProduct = new ProductAggregate(1, "name", 1,
                singletonList(new RecommendationSummary(1, "a", 1, "c")),
                singletonList(new ReviewSummary(1, "a", "s", "c")), null);

        postAndVerifyProduct(compositeProduct);

        deleteAndVerifyProduct(compositeProduct.productId());
        deleteAndVerifyProduct(compositeProduct.productId());
    }

    @Test
    void getProductById() {

        getAndVerifyProduct(PRODUCT_ID_OK, OK)
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID_OK)
                .jsonPath("$.recommendations.length()").isEqualTo(1)
                .jsonPath("$.reviews.length()").isEqualTo(1);
    }

    @Test
    void getProductNotFound() {

        getAndVerifyProduct(PRODUCT_ID_NOT_FOUND, NOT_FOUND)
                .jsonPath("$.path").isEqualTo("/product-composite/" + PRODUCT_ID_NOT_FOUND)
                .jsonPath("$.message").isEqualTo("NOT FOUND: " + PRODUCT_ID_NOT_FOUND);
    }

    @Test
    void getProductInvalidInput() {

        getAndVerifyProduct(PRODUCT_ID_INVALID, UNPROCESSABLE_ENTITY)
                .jsonPath("$.path").isEqualTo("/product-composite/" + PRODUCT_ID_INVALID)
                .jsonPath("$.message").isEqualTo("INVALID: " + PRODUCT_ID_INVALID);
    }

    private WebTestClient.BodyContentSpec getAndVerifyProduct(int productId, HttpStatus expectedStatus) {
        return client.get()
                .uri("/product-composite/" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private void postAndVerifyProduct(ProductAggregate compositeProduct) {
        client.post()
                .uri("/product-composite")
                .body(just(compositeProduct), ProductAggregate.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    private void deleteAndVerifyProduct(int productId) {
        client.delete()
                .uri("/product-composite/" + productId)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);
    }
}