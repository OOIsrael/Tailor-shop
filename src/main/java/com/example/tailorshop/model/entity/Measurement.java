package com.example.tailorshop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String value;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
