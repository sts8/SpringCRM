package com.sts8.springcrm.service;

import com.sts8.springcrm.dto.OrderAddDto;
import com.sts8.springcrm.dto.OrderDetailsDto;
import com.sts8.springcrm.dto.OrderListingDto;
import com.sts8.springcrm.model.Article;
import com.sts8.springcrm.model.Customer;
import com.sts8.springcrm.model.Order;
import com.sts8.springcrm.model.OrderContainsArticleRelation;
import com.sts8.springcrm.repository.ArticleRepository;
import com.sts8.springcrm.repository.CustomerRepository;
import com.sts8.springcrm.repository.OrderContainsArticleRepository;
import com.sts8.springcrm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ArticleRepository articleRepository;
    private final OrderContainsArticleRepository orderContainsArticleRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ArticleRepository articleRepository,
                        OrderContainsArticleRepository orderContainsArticleRepository) {

        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.articleRepository = articleRepository;
        this.orderContainsArticleRepository = orderContainsArticleRepository;
    }

    public List<OrderListingDto> getAllOrdersSorted() {

        List<OrderListingDto> allOrdersSorted = new ArrayList<>();
        List<Order> allOrders = orderRepository.findAllByOrderByTimestampDesc();

        for (Order order : allOrders) {

            Customer customer = order.getCustomer();

            allOrdersSorted.add(
                    new OrderListingDto(
                            order.getId(),
                            order.getTimestamp(),
                            customer.getId(),
                            customer.getFirstName() + " " + customer.getLastName()));
        }

        return allOrdersSorted;
    }

    public OrderDetailsDto getOrderDetails(int orderId) {

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isEmpty()) {
            return null;
        }

        Order actualOrder = order.get();
        Customer customer = actualOrder.getCustomer();
        List<Integer> articleIds = new ArrayList<>();
        List<String> articleNames = new ArrayList<>();
        List<Integer> articleAmounts = new ArrayList<>();

        List<OrderContainsArticleRelation> contains = orderContainsArticleRepository.findAllByOrderId(orderId);

        for (OrderContainsArticleRelation relation : contains) {

            articleIds.add(relation.getArticle().getId());
            articleNames.add(relation.getArticle().getName());
            articleAmounts.add(relation.getAmount());
        }

        return new OrderDetailsDto(
                actualOrder.getId(),
                actualOrder.getTimestamp(),
                customer.getId(),
                customer.getFirstName() + " " + customer.getLastName(),
                articleIds,
                articleNames,
                articleAmounts);
    }

    public int addOrder(OrderAddDto orderAddDto) {

        Optional<Customer> customer = customerRepository.findById(orderAddDto.getCustomerId());

        if (customer.isEmpty()) {
            return -1;
        }

        Customer specifiedCustomer = customer.get();

        Order newOrder = new Order();
        newOrder.setCustomer(specifiedCustomer);

        Order newOrderSaved = orderRepository.saveAndFlush(newOrder);

        for (int i = 0; i < orderAddDto.getArticleIds().size(); i++) {

            Integer articleId = orderAddDto.getArticleIds().get(i);

            if (articleId == null) {
                continue;
            }

            Optional<Article> article = articleRepository.findById(articleId);

            if (article.isEmpty()) {
                continue;
            }

            Article specifiedArticle = article.get();

            OrderContainsArticleRelation relation = new OrderContainsArticleRelation();
            relation.setOrder(newOrderSaved);
            relation.setArticle(specifiedArticle);
            relation.setAmount(orderAddDto.getArticleAmounts().get(i));

            orderContainsArticleRepository.save(relation);
        }

        return newOrder.getId();
    }

    public void deleteOrder(int id) throws IllegalArgumentException, IllegalStateException {

        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Specified order does not exist!");
        }

        orderRepository.deleteById(id);

        if (orderRepository.existsById(id)) {
            throw new IllegalStateException("Order could not be deleted!");
        }
    }

}
