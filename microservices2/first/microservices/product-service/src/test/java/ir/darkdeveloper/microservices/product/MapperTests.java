package ir.darkdeveloper.microservices.product;

import static org.junit.jupiter.api.Assertions.*;

import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservices.product.services.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class MapperTests {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void mapperTests() {

        assertNotNull(mapper);

        var api = new Product(1, "n", 1, "sa");

        var entity = mapper.apiToEntity(api);

        assertEquals(api.productId(), entity.getProductId());
        assertEquals(api.productId(), entity.getProductId());
        assertEquals(api.name(), entity.getName());
        assertEquals(api.weight(), entity.getWeight());

        var api2 = mapper.entityToApi(entity);

        assertEquals(api.productId(), api2.productId());
        assertEquals(api.productId(), api2.productId());
        assertEquals(api.name(), api2.name());
        assertEquals(api.weight(), api2.weight());
        assertNull(api2.serviceAddress());
    }
}
