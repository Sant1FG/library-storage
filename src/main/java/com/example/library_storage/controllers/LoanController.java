package com.example.library_storage.controllers;

import com.example.library_storage.entities.Loan;
import com.example.library_storage.entities.LoanRequest;
import com.example.library_storage.entities.LoanUpdateRequest;
import com.example.library_storage.exceptions.AlreadyExistsException;
import com.example.library_storage.exceptions.NotAvailableException;
import com.example.library_storage.exceptions.NotFoundException;
import com.example.library_storage.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Loan>> getLoans(){
        var loans = loanService.getLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long loanId){
        try {
            Loan loan = loanService.getLoan(loanId);
            return ResponseEntity.ok(loan);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Loan> deleteLoan(@PathVariable Long loanId){
        try {
            loanService.deleteLoan(loanId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Loan> saveLoan(@Valid @RequestBody LoanRequest loanRequest){
        try {
            Loan newLoan = loanService.saveLoan(loanRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
        } catch (AlreadyExistsException | NotAvailableException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{loanId}/return")
    public ResponseEntity<Loan> returnLoan(@PathVariable Long loanId){
        try {
            Loan loan = loanService.loanReturned(loanId);
            return ResponseEntity.ok(loan);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long loanId, @RequestBody LoanUpdateRequest loanUpdateRequest){
        try {
            Loan updatedLoan = loanService.updateLoan(loanId, loanUpdateRequest);
            return ResponseEntity.ok(updatedLoan);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
