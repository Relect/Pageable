package com.example.Pageable.controller;

import com.example.Pageable.exception.BookNotFoundException;
import com.example.Pageable.model.Book;
import com.example.Pageable.service.BookService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class Controller {

    private final BookService service;

    @GetMapping
    @JsonView(Book.Summary.class)
    public ResponseEntity<Page<Book>> getBooks(@RequestParam(defaultValue = "0", required = false) int page,
                               @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> page1 = service.getBooks(pageable);
        return ResponseEntity.ok(page1);
    }

    @GetMapping("/{id}")
    @JsonView(Book.Details.class)
    public ResponseEntity<Book> getBook(@PathVariable long id) throws BookNotFoundException {
        return ResponseEntity.ok(service.getBook(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBook(book));
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(service.updateBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable long id) {
        service.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}
