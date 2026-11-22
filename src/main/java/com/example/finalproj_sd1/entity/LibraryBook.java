package com.example.finalproj_sd1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "library_books")
public class LibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accessionCode;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String status; // "AVAILABLE" o "CHECKED OUT"

    protected LibraryBook() { }

    public LibraryBook(String accessionCode, String title, String author, String category, String status) {
        this.accessionCode = accessionCode;
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getAccessionCode() { return accessionCode; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }

    public void setAccessionCode(String accessionCode) { this.accessionCode = accessionCode; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setStatus(String status) { this.status = status; }
}