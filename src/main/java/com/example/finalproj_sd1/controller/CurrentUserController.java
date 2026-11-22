package com.example.finalproj_sd1.controller;

import com.example.finalproj_sd1.entity.AppUser;
import com.example.finalproj_sd1.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CurrentUserController {

    private final AppUserRepository userRepository;

    public CurrentUserController(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/me")
    public Map<String, String> currentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }

        AppUser user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Map<String, String> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("fullName", user.getFullName());
        response.put("role", user.getRole());
        return response;
    }

    @GetMapping("/api/students")
    public ResponseEntity<?> listStudents() {
        var students = new ArrayList<>();
        for (AppUser u : userRepository.findAll()) {
            if ("STUDENT".equalsIgnoreCase(u.getRole())) {
                Map<String, String> item = new HashMap<>();
                item.put("username", u.getUsername());
                item.put("fullName", u.getFullName());
                item.put("role", u.getRole());
                students.add(item);
            }
        }
        return ResponseEntity.ok(students);
    }
}