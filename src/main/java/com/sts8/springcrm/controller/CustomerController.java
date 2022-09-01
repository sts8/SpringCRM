package com.sts8.springcrm.controller;

import com.sts8.springcrm.dto.CustomerAddDto;
import com.sts8.springcrm.dto.CustomerDetailsDto;
import com.sts8.springcrm.dto.CustomerListingDto;
import com.sts8.springcrm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String customerListing(Model model, @RequestParam(defaultValue = "0") String page) {

        Page<CustomerListingDto> customerPage;

        try {
            customerPage = customerService.getCustomerListingPage(page);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            customerPage = customerService.getCustomerListingPage("0");
        }

        if (customerPage.isEmpty()) {
            model.addAttribute("generalMsg", "No customers!");
        }

        model.addAttribute("page", customerPage);
        return "list-customers";
    }

    @GetMapping("/customers/{id}")
    public String customerDetails(Model model, @PathVariable int id) {

        CustomerDetailsDto customer = customerService.getCustomerDetails(id);
        model.addAttribute("customer", customer);

        return "details-customer";
    }

    @GetMapping("/customers/add")
    public String addCustomer(Model model) {
        model.addAttribute("customerAddDto", new CustomerAddDto());
        return "add-customer";
    }

    @PostMapping("/customers/add")
    public String addCustomerSubmit(RedirectAttributes redirectAttributes, @ModelAttribute CustomerAddDto customerAddDto) {

        int newCustomerId = customerService.addArticle(customerAddDto);
        redirectAttributes.addFlashAttribute("successMsg", "Successfully added Customer!");

        return "redirect:/customers/" + newCustomerId;
    }

    @GetMapping("/customers/{id}/delete")
    public String deleteCustomer(RedirectAttributes redirectAttributes, @PathVariable int id) {

        try {
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("successMsg", "Customer deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }

        return "redirect:/customers";
    }

}
