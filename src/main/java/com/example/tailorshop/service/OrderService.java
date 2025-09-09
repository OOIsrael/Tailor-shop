package com.example.tailorshop.service;

import com.example.tailorshop.model.entity.Order;

import java.util.List;

// OrderService.java
public interface OrderService {
    List<Order> getOrders();
    Order getOrderById(Long id);
    void saveOrder(Order order);
    void deleteOrder(Long id);
}

