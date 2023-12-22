package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.mapper.AuthorMapper;
import com.codexsoft.bookdatabank.model.dto.AuthorBookDTO;
import com.codexsoft.bookdatabank.model.dto.AuthorDTO;
import com.codexsoft.bookdatabank.model.entity.Author;
import com.codexsoft.bookdatabank.repository.AuthorRepository;
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

        List<Author> authors = authorRepository.findAll();

        return authorMapper.map(authors);
    }

    public List<AuthorDTO> getAuthors(Integer pageNumber, Integer pageSize) {

        List<Author> authors = authorRepository.getAuthors(pageNumber, pageSize);

        return authorMapper.map(authors);
    }

    public AuthorDTO getAuthor(Long authorId) {

        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if(authorOptional.isEmpty())
            return null;

        Author author = authorOptional.get();

        return  authorMapper.map(author);
    }

    public List<AuthorBookDTO> getAuthorBooks(Long authorId) {

        return authorRepository.findAuthorBooks(authorId);
    }

    public Long createAuthor(AuthorDTO authorDTO) {

        Author author = authorMapper.map(authorDTO);

        Author newAuthor = authorRepository.save(author);

        return newAuthor.getAuthorId();
    }

    public Long updateAuthor(Long authorId, AuthorDTO authorDTO) {

        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if(authorOptional.isEmpty())
            return 0L;

        Author author = authorOptional.get();
        author.setName(authorDTO.getName());
        author.setSurname(authorDTO.getSurname());
        author.setAbout(authorDTO.getAbout());

        Author updatedAuthor = authorRepository.save(author);

        return updatedAuthor.getAuthorId();
    }
}
