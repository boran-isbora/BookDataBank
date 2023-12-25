package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.model.dto.ReportBookDTO;
import com.codexsoft.bookdatabank.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final BookRepository bookRepository;

    public List<ReportBookDTO> geReportAllBooks() {

        var reportBookDTOS = bookRepository.getAllBookReport();
        reportBookDTOS.sort(Comparator.comparing(ReportBookDTO::getPublicationDate).reversed());

        return reportBookDTOS;
    }
}
