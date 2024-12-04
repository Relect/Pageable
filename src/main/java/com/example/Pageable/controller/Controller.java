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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class Controller {

    private final BookService service;

    @GetMapping
    @JsonView(Book.Summary.class)
    public ResponseEntity<Map<String , Object>> getBooks(@RequestParam(defaultValue = "0", required = false) int page,
                               @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = service.getBooks(pageable);


        Map<String, Object> response = new HashMap<>();
        response.put("content", booksPage.getContent());
        response.put("totalPages", booksPage.getTotalPages());
        response.put("totalElements", booksPage.getTotalElements());
        response.put("currentPage", booksPage.getNumber());
        response.put("pageSize", booksPage.getSize());

        return ResponseEntity.ok(response);
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
