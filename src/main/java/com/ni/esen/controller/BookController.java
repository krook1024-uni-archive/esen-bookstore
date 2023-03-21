package com.ni.esen.controller;

import com.ni.esen.model.Book;
import com.ni.esen.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
  private final BookRepository bookRepository;

  @GetMapping
  public ResponseEntity<List<Book>> getAllBooks() {
    return ResponseEntity.ok(bookRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable Long id) {
    return ResponseEntity.ok(bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Book with id %d not found".formatted(id))));
  }

  @PostMapping
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    return ResponseEntity.ok(bookRepository.save(book));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Book with id %d not found".formatted(id)));

    book.setTitle(bookDetails.getTitle());
    book.setAuthor(bookDetails.getAuthor());
    book.setPublisher(bookDetails.getPublisher());
    book.setPublicationDate(bookDetails.getPublicationDate());
    book.setPrice(bookDetails.getPrice());

    return ResponseEntity.ok(bookRepository.save(book));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBook(@PathVariable Long id) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Book with id %d not found".formatted(id)));

    bookRepository.delete(book);

    return ResponseEntity.ok().build();
  }
}