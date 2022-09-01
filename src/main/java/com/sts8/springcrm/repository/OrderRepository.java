package com.sts8.springcrm.repository;

import com.sts8.springcrm.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findAllByOrderByTimestampDesc();

    int countByCustomerIdOrderById(int customerId);

    List<Order> findAllByCustomerId(int customerId);

    Order saveAndFlush(Order newOrder);

}
