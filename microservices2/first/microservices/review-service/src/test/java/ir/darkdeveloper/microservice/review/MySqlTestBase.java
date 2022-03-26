package ir.darkdeveloper.microservice.review;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class MySqlTestBase {
    private static final MySQLContainer<?> database = new MySQLContainer<>("mysql:latest").withReuse(true);

    static {
        database.start();
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
//        "r2dbc:tc:mysql:///databasename?TC_IMAGE_TAG=5.7.34"
        registry.add("spring.r2dbc.url", MySqlTestBase::r2dbcUrl);
        registry.add("spring.r2dbc.username", database::getUsername);
        registry.add("spring.r2dbc.password", database::getPassword);
    }


    private static String r2dbcUrl() {
        return String.format("r2dbc:mysql://%s?TC_IMAGE_TAG=5.7.34", database.getDatabaseName());
    }

}
