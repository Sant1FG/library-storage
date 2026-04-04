package com.example.library_storage.repositories;

import com.example.library_storage.entities.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, String> {

    Book findByISBN(String isbn);
    
    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByPublisher(String publisher);

    List<Book> findByCopiesGreaterThan(int copies);
}
