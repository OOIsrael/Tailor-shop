package com.example.tailorshop.service;

import com.example.tailorshop.entity.Order;
import com.example.tailorshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// OrderService.java
public interface OrderService {
    List<Order> getOrders();
    Order getOrderById(Long id);
    void saveOrder(Order order);
    void deleteOrder(Long id);
}

