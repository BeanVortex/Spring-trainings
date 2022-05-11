package ir.darkdeveloper._01bootstrap.util;

import ir.darkdeveloper._01bootstrap.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.stream.Stream;

@Slf4j
public class Demo {

    public static void workWithCustomerService(Class<?> label, CustomerService customerService) {
        log.info("==================");
        log.info(label.getName());
        log.info("==================");

        Stream.of("A", "B", "C")
                .map(str -> {
                    if (str.equals("C"))
                        return null;
                    return customerService.save(str);
                })
                .forEach(customer -> log.info("saved" + customer));

        customerService.findAll()
                .forEach(customer -> {
                    var customerId = customer.id();
                    var customerById = customerService.findById(customerId);
                    log.info("found " + customerById);
                    Assert.notNull(customerById, "The resulting customer should not be null");
                    Assert.isTrue(customerById.equals(customer), "we should be able to query this result");
                });
    }

}
