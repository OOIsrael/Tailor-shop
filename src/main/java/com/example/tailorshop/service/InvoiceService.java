package com.example.tailorshop.service;

import com.example.tailorshop.entity.Invoice;
import com.example.tailorshop.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// InvoiceService.java
public interface InvoiceService {
    List<Invoice> getInvoices();
    Invoice getInvoiceById(Long id);
    void saveInvoice(Invoice invoice);
    void deleteInvoice(Long id);
}

