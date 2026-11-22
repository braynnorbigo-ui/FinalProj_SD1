package com.example.finalproj_sd1.config;

import com.example.finalproj_sd1.entity.AppUser;
import com.example.finalproj_sd1.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/BookWebCATC.html",
                                "/BookWebCATC_LoginView.html",
                                "/images/**",
                                "/css/**",
                                "/js/**",
                                "/error"
                        ).permitAll()
                        .requestMatchers("/api/students").hasAnyRole("LIBRARIAN")
                        .requestMatchers("/api/me").authenticated()
                        .requestMatchers("/BookWebCATC_Librariandashboard.html").hasRole("LIBRARIAN")
                        .requestMatchers("/BookWebCATC_Studentdashboard.html").hasRole("STUDENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/BookWebCATC_LoginView.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/BookWebCATC_LoginView.html", true)
                        .failureUrl("/BookWebCATC_LoginView.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/BookWebCATC_LoginView.html?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public CommandLineRunner seedUsers(AppUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("librarian1").isEmpty()) {
                repo.save(new AppUser("librarian", encoder.encode("LIB-111-222"), "LIBRARIAN", "Engr.Jerelle Flores"));
            }
            if (repo.findByUsername("student1").isEmpty()) {
                repo.save(new AppUser("jaylauro", encoder.encode("C24-000111"), "STUDENT", "Jay Lauro Basquiñas"));
            }
            if (repo.findByUsername("student2").isEmpty()) {
                repo.save(new AppUser("seanbaliza", encoder.encode("C24-000222"), "STUDENT", "Sean Rashid Baliza"));
            }
            if (repo.findByUsername("student3").isEmpty()) {
                repo.save(new AppUser("jadebernal", encoder.encode("C24-000333"), "STUDENT", "Jade Bernal"));
            }
        };
    }
}