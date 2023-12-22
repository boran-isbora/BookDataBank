package com.codexsoft.bookdatabank.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookDetailDTO {
    private Long publisherId;
    private String publisherName;
    private Long bookId;
    private String bookTitle;
    private String isbn;
    private String language;
    private Integer printLength;
    private LocalDate publicationDate;
    private String coverImageUrl;
    private List<AuthorDTO> authors;
}
