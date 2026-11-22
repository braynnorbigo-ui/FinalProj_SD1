package com.example.finalproj_sd1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookRequest(
        @NotBlank(message = "Accession code is required")
        @Size(max = 30, message = "Accession code must be at most 30 characters")
        String accessionCode,

        @NotBlank(message = "Book title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @NotBlank(message = "Category is required")
        String category,

        @NotBlank(message = "Status is required")
        String status
) { }