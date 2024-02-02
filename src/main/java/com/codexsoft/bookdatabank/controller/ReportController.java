package com.codexsoft.bookdatabank.controller;

import com.codexsoft.bookdatabank.model.dto.ReportBookDto;
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
    public ResponseEntity<List<ReportBookDto>> getReportAllBooks() {

        List<ReportBookDto> reportBooks = reportService.getReportAllBooks();

        return new ResponseEntity<>(reportBooks, HttpStatus.OK);
    }
}
