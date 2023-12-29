package com.codexsoft.bookdatabank.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private String language;
    private Integer printLength;
    private LocalDate publicationDate;
    private String coverImageUrl;
}
