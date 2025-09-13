package com.example.tailorshop.controller;

import com.example.tailorshop.model.dto.CustomerDTO;
import com.example.tailorshop.service.CustomerService;
import com.example.tailorshop.model.entity.Customer;
import com.example.tailorshop.service.InvoiceService;
import com.example.tailorshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public String getCustomers(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("customers", customerService.getCustomers());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("invoices", invoiceService.getInvoices());
        List<Customer> customers;
        if (search != null && !search.isEmpty()) {
            //customers = customerService.searchCustomersByName(search);
            customers = customerService.searchCustomers(search);
        } else {
            customers = customerService.getCustomers();
        }
        model.addAttribute("customers", customers);
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

    @GetMapping("/customers_add")
    public String showCustomersAddPage(Model model) {
        model.addAttribute("customers", customerService.getCustomers());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("invoices", invoiceService.getInvoices());
        return "customers_add";
    }

    @PostMapping("/customers_add")
    public String saveCustomer(@ModelAttribute CustomerDTO customerDTO) {
        customerService.saveCustomer(customerDTO);
        return "redirect:/customers";
    }

    @GetMapping("/customers/search")
    public ResponseEntity<String> searchCustomers(@RequestParam("search") String search) {
        //List<Customer> customers = customerService.searchCustomersByName(search);
        List<Customer> customers = customerService.searchCustomers(search);
        StringBuilder html = new StringBuilder();
        for (Customer customer : customers) {
            html.append("<tr>");
            html.append("<td>").append(customer.getName()).append("</td>");
            html.append("<td>").append(customer.getEmail()).append("</td>");
            html.append("<td>").append(customer.getPhone()).append("</td>");
            html.append("</tr>");
        }
        return ResponseEntity.ok(html.toString());
    }
}