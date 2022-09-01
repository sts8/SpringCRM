package com.sts8.springcrm.controller;

import com.sts8.springcrm.dto.OrderAddDto;
import com.sts8.springcrm.dto.OrderDetailsDto;
import com.sts8.springcrm.dto.OrderListingDto;
import com.sts8.springcrm.service.ArticleService;
import com.sts8.springcrm.service.CustomerService;
import com.sts8.springcrm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ArticleService articleService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService, ArticleService articleService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.articleService = articleService;
    }

    @GetMapping("/orders")
    public String orderListing(Model model) {

        List<OrderListingDto> orders = orderService.getAllOrdersSorted();
        model.addAttribute("orders", orders);

        if (orders.isEmpty()) {
            model.addAttribute("generalMsg", "No orders!");
        }

        return "list-orders";
    }

    @GetMapping("/orders/{id}")
    public String orderDetails(Model model, @PathVariable int id) {

        OrderDetailsDto order = orderService.getOrderDetails(id);
        model.addAttribute("order", order);

        return "details-order";
    }

    @GetMapping("/orders/add")
    public String addOrder(Model model, @RequestParam(defaultValue = "-1") String customer) {
        model.addAttribute("orderAddDto", new OrderAddDto());
        model.addAttribute("selectedCustomer", customer);
        model.addAttribute("customers", customerService.getAllCustomersSorted());
        model.addAttribute("articles", articleService.getAllArticles());
        return "add-order";
    }

    @PostMapping("/orders/add")
    public String addOrderSubmit(RedirectAttributes redirectAttributes, @ModelAttribute OrderAddDto orderAddDto) {

        int newOrderId = orderService.addOrder(orderAddDto);
        redirectAttributes.addFlashAttribute("successMsg", "Successfully added Order!");

        return "redirect:/orders/" + newOrderId;
    }

    @GetMapping("/orders/{id}/delete")
    public String deleteOrder(RedirectAttributes redirectAttributes, @PathVariable int id) {

        try {
            orderService.deleteOrder(id);
            redirectAttributes.addFlashAttribute("successMsg", "Order deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }

        return "redirect:/orders";
    }

}
