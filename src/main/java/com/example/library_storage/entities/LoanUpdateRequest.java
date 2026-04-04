package com.example.library_storage.entities;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LoanUpdateRequest {
    @NotNull
    private LocalDate endDate;

    public LocalDate getEndDate() {
        return endDate;
    }
}
