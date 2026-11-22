package com.example.finalproj_sd1.service;

import com.example.finalproj_sd1.dto.BookRequest;
import com.example.finalproj_sd1.dto.BookResponse;
import com.example.finalproj_sd1.entity.LibraryBook;
import com.example.finalproj_sd1.repository.LibraryBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final LibraryBookRepository bookRepository;

    public LibraryService(LibraryBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public BookResponse createBook(BookRequest req) {
        if (bookRepository.existsByAccessionCode(req.accessionCode())) {
            throw new IllegalArgumentException("Duplicate accession code: " + req.accessionCode());
        }
        LibraryBook saved = bookRepository.save(new LibraryBook(
                req.accessionCode(),
                req.title(),
                req.author(),
                req.category(),
                req.status()
        ));
        return toResponse(saved);
    }

    public BookResponse markReturned(Long id) {
        LibraryBook book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
        book.setStatus("AVAILABLE");
        return toResponse(bookRepository.save(book));
    }

    private BookResponse toResponse(LibraryBook b) {
        return new BookResponse(
                b.getId(),
                b.getAccessionCode(),
                b.getTitle(),
                b.getAuthor(),
                b.getCategory(),
                b.getStatus()
        );
    }
}