package com.example.tailorshop.service;

import com.example.tailorshop.repository.OrganisationRepository;
import com.example.tailorshop.entity.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepository;

    public Organisation getOrganisation() {
        return organisationRepository.findById(1L).orElseThrow();
    }
}
