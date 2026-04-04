package com.example.library_storage.service;

import com.example.library_storage.entities.*;
import com.example.library_storage.exceptions.AlreadyExistsException;
import com.example.library_storage.exceptions.NotAvailableException;
import com.example.library_storage.exceptions.NotFoundException;
import com.example.library_storage.repositories.BookRepository;
import com.example.library_storage.repositories.LoanRepository;
import com.example.library_storage.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Loan saveLoan(LoanRequest loanRequest) throws NotAvailableException, AlreadyExistsException, NotFoundException {
        Book requestedBook = bookRepository.findByISBN(loanRequest.getISBN());
        if(requestedBook == null) throw new NotFoundException("Book not found");
        List<Loan> activeLoans = loanRepository.findByBook(requestedBook);
        User requestedUser = userRepository.findByDni(loanRequest.getDni());

        int loanedCopies = 0;

        for (Loan current : activeLoans) {
            if (current.getUser().equals(requestedUser) && !current.isReturned()) {
                throw new AlreadyExistsException("Book already loaned to user");
            }
            if (!current.isReturned()) loanedCopies++;
        }

        if (loanedCopies >= requestedBook.getCopies()) {
            throw new NotAvailableException("Book has no available copies");
        }

        Loan newLoan = new Loan(loanRequest.getEndDate(),requestedBook,requestedUser);
        newLoan.setReturned(false);
        this.loanRepository.save(newLoan);
        return newLoan;
    }

    public void deleteLoan(Long loanId) throws NotFoundException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
        loanRepository.delete(loan);
    }

    public Loan loanReturned(Long loanId) throws NotFoundException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
        loan.setReturned(true);
        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long loanId, LoanUpdateRequest updatedLoan) throws NotFoundException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
        if(updatedLoan.getEndDate() != null){
            loan.setEndDate(updatedLoan.getEndDate());
        }
        return loanRepository.save(loan);
    }

    public Iterable<Loan> getLoans(){
        return loanRepository.findAll();
    }

    public Loan getLoan(Long loanId) throws NotFoundException {
        return loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
    }

}
