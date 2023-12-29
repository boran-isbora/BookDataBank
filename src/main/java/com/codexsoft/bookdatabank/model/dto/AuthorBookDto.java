package com.codexsoft.bookdatabank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorBookDto {
    private Long authorId;
    private String authorName;
    private String authorSurname;
    private Long bookId;
    private String bookTitle;
    private String bookCoverImageUrl;
}
