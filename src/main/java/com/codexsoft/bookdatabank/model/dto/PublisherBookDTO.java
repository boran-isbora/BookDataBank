package com.codexsoft.bookdatabank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PublisherBookDTO {
    private Long publisherId;
    private String publisherName;
    private Long bookId;
    private String bookTitle;
    private String bookCoverImageUrl;
}
