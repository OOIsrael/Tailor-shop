package com.example.tailorshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/error")
public class CustomErrorController {

    @GetMapping
    public RedirectView error() {
        //return "An error occurred. Please try again later.";
        return new RedirectView("/login");
    }
}
