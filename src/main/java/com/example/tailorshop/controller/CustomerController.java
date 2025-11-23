package com.example.tailorshop.controller;

import com.example.tailorshop.model.dto.CustomerDTO;
import com.example.tailorshop.model.dto.MeasurementDTO;
import com.example.tailorshop.model.entity.Measurement;
import com.example.tailorshop.service.CustomerService;
import com.example.tailorshop.model.entity.Customer;
import com.example.tailorshop.service.InvoiceService;
import com.example.tailorshop.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String getCustomerDetails(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "viewCustomerModal";
    }

    @GetMapping("/customers/getCustomerModal/{id}")
    public String getCustomerModal(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "viewCustomerModal :: #viewCustomerModal";
    }

    @GetMapping("/customers/getCustomerDetails/{id}")
    @ResponseBody
    public CustomerDTO getCustomerDetails(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setGender(customer.getGender());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAddress(customer.getAddress());
        List<MeasurementDTO> measurements = new ArrayList<>();
        for (Measurement measurement : customer.getMeasurements()) {
            MeasurementDTO measurementDTO = new MeasurementDTO();
            measurementDTO.setId(measurement.getId());
            measurementDTO.setType(measurement.getType());
            measurementDTO.setValue(measurement.getValue());
            measurements.add(measurementDTO);
        }
        customerDTO.setMeasurements(measurements);
        return customerDTO;
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

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/customers/{id}/edit")
    public String editCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        //model.addAttribute("measurements", customer.getMeasurements());
        System.out.println("DEBUG: customerController customer edit called");
        //System.out.println(customer.getMeasurements());
        try {
            model.addAttribute("measurementsJson", new ObjectMapper().writeValueAsString(customer.getMeasurements()));
        } catch (JsonProcessingException e) {
            // Handle the exception, for example:
            model.addAttribute("error", "Error processing measurements");
        }

        model.addAttribute("customer", customer);
        model.addAttribute("customers", customerService.getCustomers());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("invoices", invoiceService.getInvoices());
        return "customer_edit";
    }

    @PostMapping("/customers_add")
    public String saveCustomer(@ModelAttribute CustomerDTO customerDTO) {
        customerService.saveCustomer(customerDTO);
        return "redirect:/customers";
    }

    @PostMapping("/customers/{id}/edit")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute Customer customer, BindingResult result) {
        System.out.println("Debug: Post Controller called");
        if (result.hasErrors()) {
            System.out.println("Errors: " + result.getAllErrors());
        }
        try {
            Customer existingCustomer = customerService.getCustomerById(id);
            existingCustomer.setName(customer.getName());
            existingCustomer.setPhone(customer.getPhone());
            existingCustomer.setGender(customer.getGender());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setAddress(customer.getAddress());
            // Update other customer fields...

            // Update measurements
            existingCustomer.getMeasurements().clear();
            for (Measurement measurement : customer.getMeasurements()) {
                measurement.setCustomer(existingCustomer);
            }
            existingCustomer.getMeasurements().addAll(customer.getMeasurements());

            customerService.saveCustomer(existingCustomer);
        } catch (Exception e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }

        return "redirect:/customers";
    }

    @GetMapping("/customers/search")
    public ResponseEntity<String> searchCustomers(@RequestParam("search") String search) {
        List<Customer> customers = customerService.searchCustomers(search);
        StringBuilder html = new StringBuilder();
        for (Customer customer : customers) {
            html.append("<tr>");
            html.append("<td>").append(customer.getName()).append("</td>");
            html.append("<td>").append(customer.getEmail()).append("</td>");
            html.append("<td>").append(customer.getPhone()).append("</td>");
            html.append("<td class='actions-icon'>");
            html.append("<a href='javascript:void(0)' class='btn btn-sm view-customer' title='View' data-id='").append(customer.getId()).append("'><i class='fa fa-eye'></i></a>");
            html.append("<a href='/customers/").append(customer.getId()).append("/edit' class='btn btn-sm' title='Edit'><i class='fa fa-pencil'></i></a>");
            html.append("<a href='/customers/").append(customer.getId()).append("/delete' class='btn btn-sm' title='Delete' onclick='return confirm(\"Are you sure you want to delete this customer?\")'><i class='fa fa-trash'></i></a>");
            html.append("</td>");
            html.append("</tr>");
        }
        return ResponseEntity.ok(html.toString());
    }
}