package ir.darkdeveloper._01bootstrap.service;

import ir.darkdeveloper._01bootstrap.entity.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BaseCustomerService implements CustomerService {

    private final RowMapper<Customer> rowMapper = ((rs, rowNum) ->
            new Customer(rs.getLong("id"), rs.getString("NAME")));

    private final JdbcTemplate jdbcTemplate;

    public BaseCustomerService(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    @Override
    public Collection<Customer> save(String... names) {
        var customers = new ArrayList<Customer>();
        for (var name : names) {
            var keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(psc -> {
                var ps = psc.prepareStatement(
                        "INSERT INTO customers (name) values(?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, name);
                return ps;
            }, keyHolder);
            var keyHolderKey = Objects.requireNonNull(keyHolder.getKey()).longValue();
            var customer = findById(keyHolderKey);
            Assert.notNull(name, "the given name must not be null");
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public Customer findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM customers WHERE id = ?", rowMapper, id);
    }

    @Override
    public Collection<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", rowMapper);
    }
}
