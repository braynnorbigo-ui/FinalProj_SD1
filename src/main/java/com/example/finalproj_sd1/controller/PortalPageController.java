package com.example.finalproj_sd1.controller;

import com.example.finalproj_sd1.entity.AppUser;
import com.example.finalproj_sd1.repository.AppUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class PortalPageController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public PortalPageController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String frontCanvas() {
        return "forward:/BookWebCATC.html";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "forward:/BookWebCATC_LoginView.html";
    }

    @GetMapping("/login-redirect")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/BookWebCATC_LoginView.html";
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if ("ROLE_LIBRARIAN".equals(role) || "LIBRARIAN".equals(role)) {
                return "redirect:/BookWebCATC_Librariandashboard.html";
            }
            if ("ROLE_STUDENT".equals(role) || "STUDENT".equals(role)) {
                return "redirect:/BookWebCATC_Studentdashboard.html";
            }
        }
        return "redirect:/BookWebCATC_Studentdashboard.html";
    }

    @GetMapping("/BookWebCATC_Studentdashboard")
    public String studentDashboard() {
        return "forward:/BookWebCATC_Studentdashboard.html";
    }

    @GetMapping("/BookWebCATC_Librariandashboard")
    public String librarianDashboard() {
        return "forward:/BookWebCATC_Librariandashboard.html";
    }

    @PostMapping("/api/users/register-student")
    @ResponseBody
    public ResponseEntity<?> registerStudentAccount(@RequestBody Map<String, String> payload) {
        String username = payload.getOrDefault("username", "").trim();
        String password = payload.getOrDefault("password", "").trim();
        String fullName = payload.getOrDefault("fullName", "").trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields"));
        }

        if (appUserRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        AppUser newStudent = new AppUser(
                username,
                passwordEncoder.encode(password),
                "STUDENT",
                fullName
        );
        appUserRepository.save(newStudent);

        return ResponseEntity.ok(Map.of(
                "message", "Student account registered in database",
                "username", username
        ));
    }
}