package com.codexsoft.bookdatabank.controller;

import com.codexsoft.bookdatabank.model.dto.ReportBookDTO;
import com.codexsoft.bookdatabank.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/books")
    public ResponseEntity<List<ReportBookDTO>> geReportAllBooks() {

        List<ReportBookDTO> reportBooks = reportService.geReportAllBooks();

        if(reportBooks == null || reportBooks.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(reportBooks, HttpStatus.OK);
    }
}
