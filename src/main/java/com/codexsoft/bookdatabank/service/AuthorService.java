package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.mapper.AuthorMapper;
import com.codexsoft.bookdatabank.model.dto.AuthorBookDto;
import com.codexsoft.bookdatabank.model.dto.AuthorDto;
import com.codexsoft.bookdatabank.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<AuthorDto> getAuthors() {

        var authors = authorRepository.findAll();

        return authorMapper.map(authors);
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> getAuthors(Integer pageNumber, Integer pageSize) {

        var authors = authorRepository.getAuthors(pageNumber, pageSize);

        return authorMapper.map(authors);
    }

    @Transactional(readOnly = true)
    public AuthorDto getAuthor(Long authorId) {

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found!"));

        return authorMapper.map(author);
    }

    public List<AuthorBookDto> getAuthorBooks(Long authorId) {

        return authorRepository.findAuthorBooks(authorId);
    }

    public Long createAuthor(AuthorDto authorDto) {

        var author = authorMapper.map(authorDto);

        authorRepository.save(author);

        return author.getId();
    }

    public void updateAuthor(Long authorId, AuthorDto authorDto) {

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found!"));

        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        author.setAbout(authorDto.getAbout());

        authorRepository.save(author);
    }
}
