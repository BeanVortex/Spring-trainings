package ir.darkdeveloper._01bootstrap.service;

import ir.darkdeveloper._01bootstrap.entity.Customer;

import java.util.Collection;

public interface CustomerService {
    Collection<Customer> save(String... names);

    Customer findById(Long id);

    Collection<Customer> findAll();
}
