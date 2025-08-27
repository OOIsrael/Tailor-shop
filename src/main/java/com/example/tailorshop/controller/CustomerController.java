package com.example.tailorshop.controller;

import com.example.tailorshop.service.CustomerService;
import com.example.tailorshop.entity.Customer;
import com.example.tailorshop.service.InvoiceService;
import com.example.tailorshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/customers")
    public String showCustomersPage(Model model) {
        model.addAttribute("customers", customerService.getCustomers());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("invoices", invoiceService.getInvoices());
        return "customers";
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

}
