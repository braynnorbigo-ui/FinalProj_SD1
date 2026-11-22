package com.example.finalproj_sd1.controller;

import com.example.finalproj_sd1.dto.BookRequest;
import com.example.finalproj_sd1.dto.BookResponse;
import com.example.finalproj_sd1.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class LibraryApiController {

    private final LibraryService libraryService;

    public LibraryApiController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<BookResponse> listBooks() {
        return libraryService.getAllBooks();
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest request) {
        BookResponse created = libraryService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<BookResponse> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(libraryService.markReturned(id));
    }
}