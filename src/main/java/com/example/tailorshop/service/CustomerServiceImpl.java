package com.example.tailorshop.service;

import com.example.tailorshop.model.dto.CustomerDTO;
import com.example.tailorshop.model.dto.MeasurementDTO;
import com.example.tailorshop.model.entity.Customer;
import com.example.tailorshop.model.entity.Measurement;
import com.example.tailorshop.repository.CustomerRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// CustomerServiceImpl.java
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer getCustomerById(Long id) {
        try {
            Customer customer = customerRepository.findByIdWithMeasurements(id).orElse(null);
            if (customer != null) {
                Hibernate.initialize(customer.getMeasurements());
                System.out.println("Customer: " + customer.getName());
                customer.getMeasurements().forEach(measurement -> System.out.println("Measurement: " + measurement.getType() + " - " + measurement.getValue()));
            }
            return customer;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        //customerRepository.save(customer);
        return customerRepository.save(customer);
    }

    @Override
    public Customer saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        customer.setGender(customerDTO.getGender());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());

        List<Measurement> measurements = new ArrayList<>();
        for (MeasurementDTO measurementDTO : customerDTO.getMeasurements()) {
            Measurement measurement = new Measurement();
            measurement.setType(measurementDTO.getType());
            measurement.setValue(measurementDTO.getValue());
            measurement.setCustomer(customer);
            measurements.add(measurement);
        }
        customer.setMeasurements(measurements);

        return customerRepository.save(customer);
    }

    /*public List<Customer> searchCustomersByName(String name) {
       return customerRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Customer> searchCustomers(String search) {
        return customerRepository.findByNameContainingIgnoreCaseOrPhoneContainingIgnoreCase(search, search);
    }*/

    @Override
    public List<Customer> searchCustomers(String search) {
        return customerRepository.searchCustomers(search);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
