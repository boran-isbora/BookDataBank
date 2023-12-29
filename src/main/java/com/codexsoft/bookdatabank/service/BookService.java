package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.mapper.AuthorMapper;
import com.codexsoft.bookdatabank.mapper.BookMapper;
import com.codexsoft.bookdatabank.model.dto.*;
import com.codexsoft.bookdatabank.model.entity.Book;
import com.codexsoft.bookdatabank.repository.AuthorRepository;
import com.codexsoft.bookdatabank.repository.BookRepository;
import com.codexsoft.bookdatabank.repository.PublisherRepository;
import com.codexsoft.bookdatabank.repository.specification.BookSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public PageDto<BookDto> getBooks(BookFilterDto bookFilterDto) {

        Specification<Book> specification = Specification.where(null);

        if(bookFilterDto.getLanguage() != null && !bookFilterDto.getLanguage().isEmpty()) {
            specification = specification.and(BookSpecification.hasLanguageEqualTo(bookFilterDto.getLanguage()));
        }

        if(bookFilterDto.getMinPublicationDate() != null) {
            specification = specification.and(BookSpecification.hasPublicationDateGreaterThanOrEqualTo(bookFilterDto.getMinPublicationDate()));
        }

        Page<Book> pageBook = bookRepository.findAll(specification, bookFilterDto.getPageable());

        List<BookDto> bookDtos = pageBook.map(bookMapper::map).getContent();

        PageDto<BookDto> response = new PageDto<>();
        response.setTotalItems(pageBook.getTotalElements());
        response.setTotalPages(pageBook.getTotalPages());
        response.setCurrentPage(pageBook.getNumber());
        response.setList(bookDtos);

        return response;
    }

    @Transactional(readOnly = true)
    public BookDto getBook(Long bookId) {

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found!"));

        return bookMapper.map(book);
    }

    public Optional<PublisherDto> getBookPublisher(Long bookId) {

        return bookRepository.findBookPublisher(bookId);
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> getBookAuthors(Long bookId) {

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found!"));

        var authors = book.getAuthors();

        return authorMapper.map(authors);
    }

    public Optional<BookDetailDto> getBookDetail(Long bookId) {

        return bookRepository.findBookDetail(bookId);
    }

    public Long createBook(BookDto bookDto) {

        Book book = bookMapper.map(bookDto);

        bookRepository.save(book);

        return book.getId();
    }

    public void updateBook(Long bookId, BookDto bookDto) {

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found!"));

        book.setTitle(bookDto.getTitle());
        book.setCoverImageUrl(bookDto.getCoverImageUrl());
        book.setIsbn(bookDto.getIsbn());
        book.setPublicationDate(bookDto.getPublicationDate());
        book.setLanguage(bookDto.getLanguage());
        book.setPrintLength(bookDto.getPrintLength());

        bookRepository.save(book);
    }

    public void addPublisherToBook(Long bookId, Long publisherId) {

        var publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found!"));

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found!"));


        book.setPublisher(publisher);

        bookRepository.save(book);
    }

    public Long addAuthorToBook(Long bookId, Long authorId) {

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found!"));

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found!"));


        book.getAuthors().add(author);

        bookRepository.save(book);

        return book.getId();
    }
}
