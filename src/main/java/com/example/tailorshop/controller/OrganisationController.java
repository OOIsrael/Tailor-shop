package com.example.tailorshop.controller;

import com.example.tailorshop.service.OrganisationService;
import com.example.tailorshop.model.entity.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrganisationController {
    @Autowired
    private OrganisationService organisationService;

    @GetMapping("/organisation")
    public Organisation getOrganisation() {
        return organisationService.getOrganisation();
    }
}
