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
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/BookWebCATC.html",
                                "/BookWebCATC_LoginView.html",
                                "/login",
                                "/images/**",
                                "/css/**",
                                "/js/**",
                                "/error"
                        ).permitAll()
                        .requestMatchers("/BookWebCATC_Librariandashboard.html").hasRole("LIBRARIAN")
                        .requestMatchers("/BookWebCATC_Studentdashboard.html").hasRole("STUDENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/BookWebCATC_LoginView.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/login-redirect", true)
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
            if (repo.findByUsername("librarian").isEmpty()) {
                repo.save(new AppUser("librarian", encoder.encode("LIB-111-222"), "LIBRARIAN", "Engr.Jerelle Flores"));
            }
            if (repo.findByUsername("jaylauro").isEmpty()) {
                repo.save(new AppUser("jaylauro", encoder.encode("C24-000111"), "STUDENT", "Jay Lauro Basquiñas"));
            }
            if (repo.findByUsername("seanbaliza").isEmpty()) {
                repo.save(new AppUser("seanbaliza", encoder.encode("C24-000222"), "STUDENT", "Sean Rashid Baliza"));
            }
            if (repo.findByUsername("jadebernal").isEmpty()) {
                repo.save(new AppUser("jadebernal", encoder.encode("C24-000333"), "STUDENT", "Jade Bernal"));
            }if (repo.findByUsername("braynorbigo").isEmpty()) {
                repo.save(new AppUser("braynorbigo", encoder.encode("C24-000444"), "STUDENT", "Brayn Orbigo"));
            }
        };
    }
}