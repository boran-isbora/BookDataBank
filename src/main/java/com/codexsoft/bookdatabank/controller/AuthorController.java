package com.codexsoft.bookdatabank.controller;

import com.codexsoft.bookdatabank.mapper.AuthorMapper;
import com.codexsoft.bookdatabank.model.dto.AuthorBookDTO;
import com.codexsoft.bookdatabank.model.dto.AuthorDTO;
import com.codexsoft.bookdatabank.model.request.AuthorRequest;
import com.codexsoft.bookdatabank.service.AuthorService;
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

        List<AuthorDTO> authors;

        if(pageNumber != null && pageNumber >= 0 && pageSize != null && pageSize > 0) {
            authors = authorService.getAuthors(pageNumber, pageSize);
        } else {
            authors = authorService.getAuthors();
        }

        if(authors == null || authors.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long authorId) {

        AuthorDTO author = authorService.getAuthor(authorId);

        if(author == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/{authorId}/author-books")
    public ResponseEntity<List<AuthorBookDTO>> getAuthorBooks(@PathVariable Long authorId) {

        List<AuthorBookDTO> authorBooks = authorService.getAuthorBooks(authorId);

        if(authorBooks == null || authorBooks.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(authorBooks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createAuthor(@Valid @RequestBody AuthorRequest authorRequest) {

        AuthorDTO authorDTO = authorMapper.map(authorRequest);

        Long response = authorService.createAuthor(authorDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<HttpStatus> updateAuthor(@PathVariable Long authorId, @Valid @RequestBody AuthorRequest authorRequest) {

        AuthorDTO authorDTO = authorMapper.map(authorRequest);

        Long updatedAuthorId = authorService.updateAuthor(authorId, authorDTO);

        if(updatedAuthorId == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
