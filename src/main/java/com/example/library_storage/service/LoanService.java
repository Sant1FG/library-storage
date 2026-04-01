package com.example.library_storage.service;

import com.example.library_storage.entities.Loan;
import com.example.library_storage.exceptions.AlreadyExistsException;
import com.example.library_storage.exceptions.NotAvailableException;
import com.example.library_storage.exceptions.NotFoundException;
import com.example.library_storage.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan saveLoan(Loan loan) throws NotAvailableException, AlreadyExistsException {
        List<Loan> activeLoans = loanRepository.findByBook(loan.getBook());
        int loanedCopies = 0;

        for (Loan current : activeLoans) {
            if (current.getUser().equals(loan.getUser()) && !current.isReturned()) {
                throw new AlreadyExistsException("Book already loaned to user");
            }
            if (!current.isReturned()) loanedCopies++;
        }

        if (loanedCopies >= loan.getBook().getCopies()) {
            throw new NotAvailableException("Book has no available copies");
        }

        this.loanRepository.save(loan);
        return loan;
    }

    public Loan deleteLoan(Long loanId) throws NotFoundException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
        loanRepository.delete(loan);

        return loan;
    }

    public Loan loanReturned(Long loanId) throws NotFoundException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
        loan.setReturned(true);
        loanRepository.save(loan);
        return loan;
    }

    public Loan modifyLoan(Long loanId, Loan updatedLoan) throws NotFoundException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
        loan.setEndDate(updatedLoan.getEndDate());
        loanRepository.save(loan);
        return loan;
    }

}
