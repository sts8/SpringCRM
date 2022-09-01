package com.sts8.springcrm.service;

import com.sts8.springcrm.dto.CustomerAddDto;
import com.sts8.springcrm.dto.CustomerDetailsDto;
import com.sts8.springcrm.dto.CustomerListingDto;
import com.sts8.springcrm.model.Customer;
import com.sts8.springcrm.model.Order;
import com.sts8.springcrm.repository.CustomerRepository;
import com.sts8.springcrm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private static final int PAGINATION_ELEMENTS_PER_PAGE = 10;

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public List<CustomerListingDto> getAllCustomersSorted() {

        List<CustomerListingDto> allCustomersSorted = new ArrayList<>();
        List<Customer> allCustomers = customerRepository.findAllByOrderById();

        for (Customer customer : allCustomers) {

            CustomerListingDto dto = new CustomerListingDto();
            dto.setId(customer.getId());
            dto.setFirstName(customer.getFirstName());
            dto.setLastName(customer.getLastName());
            dto.setNumberOfOrders(orderRepository.countByCustomerIdOrderById(customer.getId()));

            allCustomersSorted.add(dto);
        }

        return allCustomersSorted;
    }

    public CustomerDetailsDto getCustomerDetails(int customerId) {

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return null;
        }

        Customer actualCustomer = customer.get();
        List<Integer> orderIds = new ArrayList<>();
        List<Date> orderTimestamps = new ArrayList<>();

        for (Order order : orderRepository.findAllByCustomerId(customerId)) {
            orderIds.add(order.getId());
            orderTimestamps.add(order.getTimestamp());
        }

        return new CustomerDetailsDto(
                actualCustomer.getId(),
                actualCustomer.getFirstName(),
                actualCustomer.getLastName(),
                orderIds,
                orderTimestamps);
    }

    public Page<CustomerListingDto> getCustomerListingPage(String page) throws IllegalArgumentException {

        int pageNo;

        try {
            pageNo = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }

        if (pageNo < 0) {
            throw new IllegalArgumentException("Specified page number < 0!");
        }

        Pageable pageable = PageRequest.of(pageNo, PAGINATION_ELEMENTS_PER_PAGE);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        List<CustomerListingDto> customers = new ArrayList<>();

        for (Customer customer : customerPage) {

            CustomerListingDto dto = new CustomerListingDto();
            dto.setId(customer.getId());
            dto.setFirstName(customer.getFirstName());
            dto.setLastName(customer.getLastName());
            dto.setNumberOfOrders(orderRepository.countByCustomerIdOrderById(customer.getId()));

            customers.add(dto);
        }

        return new PageImpl<>(customers, pageable, customerPage.getTotalElements());
    }

    public int addArticle(CustomerAddDto customerAddDto) {

        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customerAddDto.getFirstName());
        newCustomer.setLastName(customerAddDto.getLastName());

        Customer newCustomerSaved = customerRepository.save(newCustomer);
        return newCustomerSaved.getId();
    }

    public void deleteCustomer(int id) throws IllegalArgumentException, IllegalStateException {

        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Specified customer does not exist!");
        }

        customerRepository.deleteById(id);

        if (customerRepository.existsById(id)) {
            throw new IllegalStateException("Customer could not be deleted!");
        }
    }

}
