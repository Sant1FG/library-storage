package com.example.library_storage.service;

import com.example.library_storage.entities.Book;
import com.example.library_storage.entities.User;
import com.example.library_storage.exceptions.AlreadyExistsException;
import com.example.library_storage.exceptions.NotFoundException;
import com.example.library_storage.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) throws AlreadyExistsException {

        if (bookRepository.existsById(book.getISBN())) {
            throw new AlreadyExistsException("Book already exists");
        }
        bookRepository.save(book);
        return book;
    }

    public void deleteBook(String ISBN) throws NotFoundException {
        Book book = bookRepository.findById(ISBN).orElseThrow(() -> new NotFoundException("Book not found"));
        bookRepository.delete(book);
    }

    public Book editBook(String ISBN, Book updatedBook) throws NotFoundException {
        Book book = bookRepository.findById(ISBN).orElseThrow(() -> new NotFoundException("Book not found"));
        book.setAuthor(updatedBook.getAuthor());
        book.setTitle(updatedBook.getTitle());
        book.setPublisher(updatedBook.getPublisher());
        book.setCopies(updatedBook.getCopies());

        bookRepository.save(book);
        return book;
    }

    public Iterable<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Book getBook(String ISBN) throws NotFoundException {
        return bookRepository.findById(ISBN).orElseThrow(() -> new NotFoundException("Book not found"));
    }
}

