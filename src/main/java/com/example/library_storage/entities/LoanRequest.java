package com.example.library_storage.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LoanRequest {
    @NotBlank
    private String ISBN;
    @NotBlank
    private String dni;
    @NotNull
    private LocalDate endDate;

    public String getISBN() {
        return ISBN;
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
