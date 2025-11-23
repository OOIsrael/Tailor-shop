package com.example.tailorshop.repository;

import com.example.tailorshop.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //List<Customer> findByNameContainingIgnoreCase(String name);

    //List<Customer> findByNameContainingIgnoreCaseOrPhoneContainingIgnoreCase(String name, String phone);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Customer> searchCustomers(@Param("search") String search);

    @Query("SELECT c FROM Customer c JOIN FETCH c.measurements WHERE c.id = :id")
    Optional<Customer> findByIdWithMeasurements(@Param("id") Long id);
}
