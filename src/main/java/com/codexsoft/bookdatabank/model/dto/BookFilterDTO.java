package com.codexsoft.bookdatabank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BookFilterDTO {
    private String language;
    private LocalDate afterPublicationDate;
    private Pageable pageable;
}
