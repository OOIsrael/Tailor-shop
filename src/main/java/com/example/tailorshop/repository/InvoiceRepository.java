package com.example.tailorshop.repository;

import com.example.tailorshop.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {}
