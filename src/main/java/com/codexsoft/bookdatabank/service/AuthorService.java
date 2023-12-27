package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.mapper.AuthorMapper;
import com.codexsoft.bookdatabank.model.dto.AuthorBookDTO;
import com.codexsoft.bookdatabank.model.dto.AuthorDTO;
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
    public List<AuthorDTO> getAuthors() {

        var authors = authorRepository.findAll();

        return authorMapper.map(authors);
    }

    @Transactional(readOnly = true)
    public List<AuthorDTO> getAuthors(Integer pageNumber, Integer pageSize) {

        var authors = authorRepository.getAuthors(pageNumber, pageSize);

        return authorMapper.map(authors);
    }

    @Transactional(readOnly = true)
    public AuthorDTO getAuthor(Long authorId) {

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found!"));

        return authorMapper.map(author);
    }

    public List<AuthorBookDTO> getAuthorBooks(Long authorId) {

        return authorRepository.findAuthorBooks(authorId);
    }

    public Long createAuthor(AuthorDTO authorDTO) {

        var author = authorMapper.map(authorDTO);

        authorRepository.save(author);

        return author.getId();
    }

    public void updateAuthor(Long authorId, AuthorDTO authorDTO) {

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found!"));

        author.setName(authorDTO.getName());
        author.setSurname(authorDTO.getSurname());
        author.setAbout(authorDTO.getAbout());

        authorRepository.save(author);
    }
}
