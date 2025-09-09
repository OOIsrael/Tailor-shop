package com.example.tailorshop.service;

import com.example.tailorshop.model.entity.User;
import com.example.tailorshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String signUpUser(String username, String password, String confirmPassword) {
        System.out.println("DEBUG: signUpUser called with username=" + username);
        if (!password.equals(confirmPassword)) {
            System.out.println("DEBUG: Passwords do not match.");
            return "Passwords do not match.";
        }
        if (userRepository.findByUsername(username).isPresent()) {
            System.out.println("DEBUG: Username is already taken.");
            return "Username is already taken.";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        System.out.println("DEBUG: Saving user=" + user);
        userRepository.save(user);
        System.out.println("DEBUG: User saved successfully.");
        return "success";
    }

    @Autowired
    private EmailService emailService;

    public String initiatePasswordReset(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "User not found.";
        }
        User user = userOptional.get();
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30));
        userRepository.save(user);
        String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;
        System.out.println(new StringBuilder().append("DEBUG: password reset link ").append(resetLink).toString());
        //emailService.sendResetPasswordEmail(user.getUsername() + "@example.com", resetLink); // Adjust email
        //emailService.sendResetPasswordEmail("dareoni27@gmail.com", resetLink); // Adjust email
        return "success";
    }

    public String resetPassword(String token, String newPassword) {
        Optional<User> userOptional = userRepository.findByResetToken(token);
        if (userOptional.isEmpty()) {
            return "Invalid token.";
        }
        User user = userOptional.get();
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return "Token expired.";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
        return "success";
    }
}
