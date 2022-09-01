package com.sts8.springcrm.repository;

import com.sts8.springcrm.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {

    List<Customer> findAllByOrderById();

}
