package com.example.finalproj_sd1.dto;

public record BookResponse(
        Long id,
        String accessionCode,
        String title,
        String author,
        String category,
        String status
) { }