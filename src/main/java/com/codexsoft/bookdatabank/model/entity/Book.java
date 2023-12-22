package com.codexsoft.bookdatabank.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "BOOKS")
@Getter
@Setter
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private LocalDate publicationDate;
    private String language;
    private Integer printLength;
    private String isbn;
    private String coverImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Author> authors;
}
