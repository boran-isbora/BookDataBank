package com.codexsoft.bookdatabank.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class BookRequest {

    @NotNull(message = "NAME should not be null")
    @NotEmpty(message = "NAME should not be empty")
    private String title;

    private String isbn;
    private String language;
    private int printLength;

    @NotNull(message = "PUBLICATION DATE should not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    private String coverImageUrl;
}
