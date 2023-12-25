package com.codexsoft.bookdatabank.controller;

import com.codexsoft.bookdatabank.mapper.AuthorMapper;
import com.codexsoft.bookdatabank.model.dto.AuthorBookDTO;
import com.codexsoft.bookdatabank.model.dto.AuthorDTO;
import com.codexsoft.bookdatabank.model.request.AuthorRequest;
import com.codexsoft.bookdatabank.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAuthors(@RequestParam(required = false, name = "page") Integer pageNumber,
                                                      @RequestParam(required = false, name = "size") Integer pageSize) {

        var authors = getAuthorDTOS(pageNumber, pageSize);

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    private List<AuthorDTO> getAuthorDTOS(Integer pageNumber, Integer pageSize) {

        if(pageNumber != null && pageNumber >= 0 && pageSize != null && pageSize > 0) {
            return authorService.getAuthors(pageNumber, pageSize);
        } else {
            return authorService.getAuthors();
        }
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long authorId) {

        try {

            var authorDTO = authorService.getAuthor(authorId);

            return new ResponseEntity<>(authorDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{authorId}/author-books")
    public ResponseEntity<List<AuthorBookDTO>> getAuthorBooks(@PathVariable Long authorId) {

        var authorBooks = authorService.getAuthorBooks(authorId);

        return new ResponseEntity<>(authorBooks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createAuthor(@Valid @RequestBody AuthorRequest authorRequest) {

        var authorDTO = authorMapper.map(authorRequest);

        Long response = authorService.createAuthor(authorDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Void> updateAuthor(@PathVariable Long authorId, @Valid @RequestBody AuthorRequest authorRequest) {

        var authorDTO = authorMapper.map(authorRequest);

        try {
            authorService.updateAuthor(authorId, authorDTO);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
