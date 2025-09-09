package com.example.tailorshop.controller;

import com.example.tailorshop.model.entity.ContactMessage;
import com.example.tailorshop.repository.ContactMessageRepository;
import com.example.tailorshop.service.CustomerService;
import com.example.tailorshop.service.InvoiceService;
import com.example.tailorshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @GetMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("customers", customerService.getCustomers());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("invoices", invoiceService.getInvoices());
        return "contact"; // Return the view name (contact.html with Thymeleaf)
    }

    @PostMapping("/contact")
    public String handleContactForm(@RequestParam("name") String name,
                                    @RequestParam("email") String email,
                                    @RequestParam("message") String message) {
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(name);
        contactMessage.setEmail(email);
        contactMessage.setMessage(message);
        contactMessageRepository.save(contactMessage);
        return "redirect:/contact?success";
    }

    @GetMapping("/about-us")
    public String showAboutUsPage(Model model) {
        model.addAttribute("customers", customerService.getCustomers());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("invoices", invoiceService.getInvoices());
        return "about-us"; // Return the view name (contact.html with Thymeleaf)
    }

}