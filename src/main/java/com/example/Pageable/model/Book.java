package com.example.Pageable.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "books")
public class Book {
    public static class Summary{}
    public static class Details extends Summary{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Summary.class)
    private Long id;
    @JsonView(Summary.class)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonView(Details.class)
    private Author author;


}
