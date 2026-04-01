package com.example.library_storage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    @Id
    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private int copies;
    @OneToMany(mappedBy = "book")
    private List<Loan> loans = new ArrayList<>();

    public Book(String ISBN, String title, String author, String publisher, int copies) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.copies = copies;
    }

    protected  Book(){
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getCopies() {
        return copies;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
