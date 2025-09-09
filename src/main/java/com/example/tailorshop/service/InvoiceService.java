package com.example.tailorshop.service;

import com.example.tailorshop.model.entity.Invoice;

import java.util.List;

// InvoiceService.java
public interface InvoiceService {
    List<Invoice> getInvoices();
    Invoice getInvoiceById(Long id);
    void saveInvoice(Invoice invoice);
    void deleteInvoice(Long id);
}

