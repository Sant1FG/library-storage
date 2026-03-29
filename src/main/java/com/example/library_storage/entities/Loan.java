package com.example.library_storage.entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loan_id;
    private LocalDate creationDate;
    private LocalDate endDate;
    private boolean returned;
    @ManyToOne
    @JoinColumn(name = "ISBN")
    private Book book;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Loan(LocalDate endDate,Book book, User user){
        this.creationDate = LocalDate.now();
        this.endDate = endDate;
        this.book = book;
        this.user = user;
    }

    protected Loan(){};

    public Long getLoan_id() {
        return loan_id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public LoanStatus getStatus() {
        if(returned) return LoanStatus.COMPLETED;
        if (LocalDate.now().isAfter(endDate)) return LoanStatus.DELAYED;
        return LoanStatus.ACTIVE;
    }

}
