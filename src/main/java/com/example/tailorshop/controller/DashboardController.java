package com.example.tailorshop.controller;

import com.example.tailorshop.service.CustomerService;
import com.example.tailorshop.service.InvoiceService;
import com.example.tailorshop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class DashboardController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;



    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        //System.out.println("DEBUG: Dashboard called");
        // Fetch data for customers, orders, invoices
        model.addAttribute("customers", customerService.getCustomers());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("invoices", invoiceService.getInvoices());


        // Add slider images or data if needed
        model.addAttribute("sliderImages", Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"));
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        // Handle logout logic, like invalidating session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login?logout"; // Redirect
    }


}
