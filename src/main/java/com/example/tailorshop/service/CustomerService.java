package com.example.tailorshop.service;

import com.example.tailorshop.model.dto.CustomerDTO;
import com.example.tailorshop.model.dto.MeasurementDTO;
import com.example.tailorshop.model.entity.Measurement;
import com.example.tailorshop.repository.CustomerRepository;
import com.example.tailorshop.model.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomerById(Long id);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Long id);
    Customer saveCustomer(CustomerDTO customerDTO);
}
