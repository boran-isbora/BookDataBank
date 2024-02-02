package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.model.dto.ReportBookDto;
import com.codexsoft.bookdatabank.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final BookRepository bookRepository;

    public List<ReportBookDto> getReportAllBooks() {

        return bookRepository.getAllBookReport()
                .stream()
                .sorted(Comparator.comparing(ReportBookDto::getPublicationDate).reversed())
                .toList();
    }
}
