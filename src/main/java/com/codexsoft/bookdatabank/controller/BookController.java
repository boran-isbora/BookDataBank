package com.codexsoft.bookdatabank.controller;

import com.codexsoft.bookdatabank.mapper.BookMapper;
import com.codexsoft.bookdatabank.model.dto.*;
import com.codexsoft.bookdatabank.model.request.BookRequest;
import com.codexsoft.bookdatabank.service.BookService;
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

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBooks(@RequestParam(required = false, name = "lang") String language,
                                                        @RequestParam(required = false, name = "publication_date") LocalDate afterPublicationDate,
                                                        @PageableDefault(page = 0, size = 4) @SortDefault.SortDefaults({@SortDefault(sort = "publicationDate", direction = Sort.Direction.DESC)}) Pageable pageable) {

        BookFilterDTO bookFilterDTO = new BookFilterDTO(language, afterPublicationDate, pageable);

        Map<String, Object> response = bookService.getBooks(bookFilterDTO);

        if(response == null || response.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long bookId) {

        BookDTO book = bookService.getBook(bookId);

        if(book == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @GetMapping("/{bookId}/publisher")
    public ResponseEntity<PublisherDTO> getBookPublisher(@PathVariable Long bookId) {

        PublisherDTO publisher = bookService.getBookPublisher(bookId);

        if(publisher == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @GetMapping("/{bookId}/authors")
    public ResponseEntity<List<AuthorDTO>> getBookAuthors(@PathVariable Long bookId) {

        List<AuthorDTO> authors = bookService.getBookAuthors(bookId);

        if(authors == null || authors.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{bookId}/book-detail")
    public ResponseEntity<BookDetailDTO> getBookDetail(@PathVariable Long bookId) {

        BookDetailDTO bookDetail = bookService.getBookDetail(bookId);

        if(bookDetail == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(bookDetail, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createBook(@Valid @RequestBody BookRequest bookRequest) {

        BookDTO bookDTO = bookMapper.map(bookRequest);

        Long response = bookService.createBook(bookDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<HttpStatus> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookRequest bookRequest) {

        BookDTO bookDTO = bookMapper.map(bookRequest);

        Long updatedBookId = bookService.updateBook(bookId, bookDTO);

        if(updatedBookId == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{bookId}/publisher/{publisherId}")
    public ResponseEntity<Long> addPublisherToBook(@PathVariable Long bookId, @PathVariable Long publisherId) {

        Long response = bookService.addPublisherToBook(bookId, publisherId);

        if(response == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Long> addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId) {

        Long response = bookService.addAuthorToBook(bookId, authorId);

        if(response == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
