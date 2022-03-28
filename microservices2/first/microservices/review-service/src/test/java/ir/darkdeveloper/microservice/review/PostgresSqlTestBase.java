package ir.darkdeveloper.microservice.review;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class PostgresSqlTestBase {
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:13.1-alpine")
//            .withInitScript("schema.sql")
            .withReuse(true);

    static {
        database.start();
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", PostgresSqlTestBase::r2dbcUrl);
        registry.add("spring.r2dbc.username", database::getUsername);
        registry.add("spring.r2dbc.password", database::getPassword);
    }


    private static String r2dbcUrl() {
        return String.format("r2dbc:tc:postgresql:///%s?TC_IMAGE_TAG=13.1-alpine", database.getDatabaseName());
    }

}
