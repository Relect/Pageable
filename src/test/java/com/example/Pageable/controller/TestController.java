package com.example.Pageable.controller;


import com.example.Pageable.exception.BookNotFoundException;
import com.example.Pageable.model.Author;
import com.example.Pageable.model.Book;
import com.example.Pageable.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestController {

    @Mock
    private BookService service;
    @InjectMocks
    private Controller controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllTest() throws Exception {

        Author author = new Author(1L, "John", null);
        Book book1 = new Book(1L, "book1", author);
        Book book2 = new Book(2L, "book2", author);
        Book book3 = new Book(3L, "book3", author);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> bookPage = new PageImpl<>(List.of(book1, book2, book3), pageable, 3);
        Mockito.when(service.getBooks(Mockito.any(Pageable.class))).thenReturn(bookPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .param("page", "0")
                .param("size", "5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("book1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].author").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("book2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].author").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].name").value("book3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].author").doesNotExist());
    }

    @Test
    public void getBook() throws Exception {
        Author author = new Author(1L, "John", null);
        Book book1 = new Book(1L, "book1", author);

        Mockito.when(service.getBook(Mockito.any(Long.class))).thenReturn(book1);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("book1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.name").value("John"));
    }

}
