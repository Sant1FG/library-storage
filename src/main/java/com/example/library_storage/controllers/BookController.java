package com.example.library_storage.controllers;


import com.example.library_storage.entities.Book;
import com.example.library_storage.exceptions.AlreadyExistsException;
import com.example.library_storage.exceptions.NotFoundException;
import com.example.library_storage.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Book>> getBooks(){
        var books = bookService.getBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{ISBN}")
    public ResponseEntity<Book> getBook(@PathVariable String ISBN){
        try {
            Book book = bookService.getBook(ISBN);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{ISBN}")
    public ResponseEntity<Book> deleteBook(@PathVariable String ISBN){
        try {
            bookService.deleteBook(ISBN);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book){
        try {
            Book newBook = bookService.saveBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
        } catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{ISBN}")
    public ResponseEntity<Book> editBook(@PathVariable String ISBN, @Valid @RequestBody Book book){
        try {
            Book editedBook = bookService.editBook(ISBN,book);
            return ResponseEntity.ok(editedBook);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
