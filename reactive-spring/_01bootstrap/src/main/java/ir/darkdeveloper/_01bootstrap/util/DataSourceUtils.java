package ir.darkdeveloper._01bootstrap.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public abstract class DataSourceUtils {
    public static void initializeDbl(DataSource ds) {
        var populator = new ResourceDatabasePopulator(
                new ClassPathResource("/schema.sql"));
        DatabasePopulatorUtils.execute(populator, ds);
    }
}
