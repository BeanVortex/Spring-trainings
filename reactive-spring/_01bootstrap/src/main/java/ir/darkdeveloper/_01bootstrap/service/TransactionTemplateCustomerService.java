package ir.darkdeveloper._01bootstrap.service;

import ir.darkdeveloper._01bootstrap.entity.Customer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Collection;

public class TransactionTemplateCustomerService extends BaseCustomerService {
    private final TransactionTemplate transactionTemplate;

    public TransactionTemplateCustomerService(DataSource ds, TransactionTemplate transactionTemplate) {
        super(ds);
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Collection<Customer> save(String... names) {
        return transactionTemplate.execute(status -> super.save(names));
    }

    @Override
    public Customer findById(Long id) {
        return transactionTemplate.execute(s -> super.findById(id));
    }

    @Override
    public Collection<Customer> findAll() {
        return transactionTemplate.execute(s -> super.findAll());
    }
}
