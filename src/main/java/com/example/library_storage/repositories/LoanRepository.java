package com.example.library_storage.repositories;

import com.example.library_storage.entities.Book;
import com.example.library_storage.entities.Loan;
import com.example.library_storage.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findByReturned(boolean returned);

    List<Loan> findByCreationDateAfter(LocalDate creationDateAfter);

    List<Loan> findByCreationDateBefore(LocalDate creationDateBefore);

    List<Loan> findByEndDateAfter(LocalDate endDateAfter);

    List<Loan> findByEndDateBefore(LocalDate endDateBefore);

    List<Loan> findByBook(Book book);

    List<Loan> findByUser(User user);
}
