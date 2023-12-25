package com.codexsoft.bookdatabank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class BookPageDTO {
    private Long totalItems;
    private Integer currentPage;
    private Integer totalPages;
    private List<BookDTO> bookDTOS;
}
