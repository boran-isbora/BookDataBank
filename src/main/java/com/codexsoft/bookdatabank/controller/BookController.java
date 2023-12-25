package com.codexsoft.bookdatabank.controller;

import com.codexsoft.bookdatabank.mapper.BookMapper;
import com.codexsoft.bookdatabank.model.dto.*;
import com.codexsoft.bookdatabank.model.request.BookAddAuthorRequest;
import com.codexsoft.bookdatabank.model.request.BookAddPublisherRequest;
import com.codexsoft.bookdatabank.model.request.BookRequest;
import com.codexsoft.bookdatabank.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/page")
    public ResponseEntity<BookPageDTO> getBooks(@RequestParam(required = false, name = "lang") String language,
                                                @RequestParam(required = false, name = "publication_date") LocalDate afterPublicationDate,
                                                @PageableDefault(page = 0, size = 4) @SortDefault.SortDefaults({@SortDefault(sort = "publicationDate", direction = Sort.Direction.DESC)}) Pageable pageable) {

        var bookFilterDTO = new BookFilterDTO(language, afterPublicationDate, pageable);

        var response = bookService.getBooks(bookFilterDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long bookId) {

        try {

            var bookDTO = bookService.getBook(bookId);

            return new ResponseEntity<>(bookDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{bookId}/publisher")
    public ResponseEntity<PublisherDTO> getBookPublisher(@PathVariable Long bookId) {

        return bookService.getBookPublisher(bookId)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{bookId}/authors")
    public ResponseEntity<List<AuthorDTO>> getBookAuthors(@PathVariable Long bookId) {

        try {

            List<AuthorDTO> authors = bookService.getBookAuthors(bookId);

            return new ResponseEntity<>(authors, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{bookId}/book-detail")
    public ResponseEntity<BookDetailDTO> getBookDetail(@PathVariable Long bookId) {

        return bookService.getBookDetail(bookId)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Long> createBook(@Valid @RequestBody BookRequest bookRequest) {

        var bookDTO = bookMapper.map(bookRequest);

        Long response = bookService.createBook(bookDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookRequest bookRequest) {

        var bookDTO = bookMapper.map(bookRequest);

        try {
            bookService.updateBook(bookId, bookDTO);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{bookId}/publisher")
    public ResponseEntity<Void> addPublisherToBook(@PathVariable Long bookId, @Valid @RequestBody BookAddPublisherRequest request) {

        try {
            bookService.addPublisherToBook(bookId, request.getPublisherId());
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/authors")
    public ResponseEntity<Long> addAuthorToBook(@PathVariable Long bookId, @Valid @RequestBody BookAddAuthorRequest request) {

        Long response = bookService.addAuthorToBook(bookId, request.getAuthorId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
