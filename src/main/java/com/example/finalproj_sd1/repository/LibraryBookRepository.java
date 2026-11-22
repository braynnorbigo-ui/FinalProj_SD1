package com.example.finalproj_sd1.repository;

import com.example.finalproj_sd1.entity.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {
    boolean existsByAccessionCode(String accessionCode);
    List<LibraryBook> findByStatus(String status);
}