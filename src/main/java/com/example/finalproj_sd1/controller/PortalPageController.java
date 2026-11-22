package com.example.finalproj_sd1.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalPageController {

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
}