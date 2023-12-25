package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.mapper.AuthorMapper;
import com.codexsoft.bookdatabank.model.dto.AuthorBookDTO;
import com.codexsoft.bookdatabank.model.dto.AuthorDTO;
import com.codexsoft.bookdatabank.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorDTO> getAuthors() {

        var authors = authorRepository.findAll();

        return authorMapper.map(authors);
    }

    public List<AuthorDTO> getAuthors(Integer pageNumber, Integer pageSize) {

        var authors = authorRepository.getAuthors(pageNumber, pageSize);

        return authorMapper.map(authors);
    }

    public AuthorDTO getAuthor(Long authorId) {

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found!"));

        return authorMapper.map(author);
    }

    public List<AuthorBookDTO> getAuthorBooks(Long authorId) {

        return authorRepository.findAuthorBooks(authorId);
    }

    @Transactional
    public Long createAuthor(AuthorDTO authorDTO) {

        var author = authorMapper.map(authorDTO);

        authorRepository.save(author);

        return author.getAuthorId();
    }

    @Transactional
    public void updateAuthor(Long authorId, AuthorDTO authorDTO) {

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found!"));

        author.setName(authorDTO.getName());
        author.setSurname(authorDTO.getSurname());
        author.setAbout(authorDTO.getAbout());

        authorRepository.save(author);
    }
}
