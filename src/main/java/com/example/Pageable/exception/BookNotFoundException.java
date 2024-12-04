package com.example.Pageable.exception;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(long id) {
        super("book not found id:" + id);
    }
}
