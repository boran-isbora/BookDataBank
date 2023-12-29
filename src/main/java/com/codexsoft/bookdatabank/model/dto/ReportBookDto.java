package com.codexsoft.bookdatabank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ReportBookDto {
    private String publisherName;
    private String bookTitle;
    private String language;
    private Integer printLength;
    private LocalDate publicationDate;
    private String authors;
}