package com.example.tailorshop.repository;

import com.example.tailorshop.model.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
}
