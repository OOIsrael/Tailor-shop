package com.example.tailorshop.controller;

import com.example.tailorshop.repository.UserRepository;
import com.example.tailorshop.service.AuthService;
import com.example.tailorshop.service.CustomerService;
import com.example.tailorshop.service.InvoiceService;
import com.example.tailorshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthService authService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forget-password")
    public String forgetPassword() {
        return "forget-password";
    }

    @GetMapping("/sign-up")
    public String signUpPage() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, Model model) {
        //System.out.println("DEBUG: AuthController signUp called");
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "sign-up";
        }
        String result = authService.signUpUser(username, password, confirmPassword);
        if (!result.equals("success")) {
            model.addAttribute("error", result);
            return "sign-up";
        }
        return "redirect:/login";
    }
    @PostMapping("/forget-password")
    public String forgetPassword(@RequestParam String username, Model model) {
        // Call service method to handle password reset initiation
        //System.out.println("DEBUG: AuthController forget password called");
        String result = authService.initiatePasswordReset(username);
        System.out.println(new StringBuilder().append("DEBUG: after resetting ").append(result).toString());
        if (!result.equals("success")) {
            //System.out.println("DEBUG: AuthController forget password failed");
            model.addAttribute("error", result);
            return "forget-password";
        }
        return "redirect:/login?reset-initiated";
        //return "redirect:/login";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        // Optionally validate token existence/expiry here
        model.addAttribute("token", token);
        return "reset-password"; // Return the reset-password view
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "reset-password";
        }
        String result = authService.resetPassword(token, newPassword);
        if (!result.equals("success")) {
            model.addAttribute("error", result);
            return "reset-password";
        }
        return "redirect:/login?password-reset";
    }

}
