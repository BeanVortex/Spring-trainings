package ir.darkdeveloper._01bootstrap;

import ir.darkdeveloper._01bootstrap.service.BaseCustomerService;
import ir.darkdeveloper._01bootstrap.service.TransactionTemplateCustomerService;
import ir.darkdeveloper._01bootstrap.util.DataSourceUtils;
import ir.darkdeveloper._01bootstrap.util.Demo;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.support.TransactionTemplate;

//@SpringBootApplication
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        var ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2).build();
        DataSourceUtils.initializeDbl(ds);
        var transactionManager = new DataSourceTransactionManager(ds);
        var transactionTemplate = new TransactionTemplate(transactionManager);
        var customerService = new TransactionTemplateCustomerService(ds,transactionTemplate);
        Demo.workWithCustomerService(Application.class, customerService);
    }


}
