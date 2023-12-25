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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    public BookPageDTO getBooks(BookFilterDTO bookFilterDTO) {

        Specification<Book> specification = Specification.where(null);

        if(bookFilterDTO.getLanguage() != null && !bookFilterDTO.getLanguage().isEmpty()) {
            specification = specification.and(BookSpecification.hasLanguage(bookFilterDTO.getLanguage()));
        }

        if(bookFilterDTO.getAfterPublicationDate() != null) {
            specification = specification.and(BookSpecification.hasPublicationDateGreaterThanOrEqualTo(bookFilterDTO.getAfterPublicationDate()));
        }

        Page<Book> pageBook = bookRepository.findAll(specification, bookFilterDTO.getPageable());

        List<BookDTO> bookDTOS = pageBook.map(bookMapper::map).getContent();

        BookPageDTO response = new BookPageDTO();
        response.setTotalItems(pageBook.getTotalElements());
        response.setTotalPages(pageBook.getTotalPages());
        response.setCurrentPage(pageBook.getNumber());
        response.setBookDTOS(bookDTOS);

        return response;
    }

    public BookDTO getBook(Long bookId) {

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found!"));

        return bookMapper.map(book);
    }

    public Optional<PublisherDTO> getBookPublisher(Long bookId) {

        return bookRepository.findBookPublisher(bookId);
    }

    public List<AuthorDTO> getBookAuthors(Long bookId) {

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found!"));

        var authors = book.getAuthors();

        return authorMapper.map(authors);
    }

    public Optional<BookDetailDTO> getBookDetail(Long bookId) {

        return bookRepository.findBookDetail(bookId);
    }

    @Transactional
    public Long createBook(BookDTO bookDTO) {

        Book book = bookMapper.map(bookDTO);

        bookRepository.save(book);

        return book.getId();
    }

    @Transactional
    public void updateBook(Long bookId, BookDTO bookDTO) {

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found!"));


        book.setTitle(bookDTO.getTitle());
        book.setCoverImageUrl(bookDTO.getCoverImageUrl());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setLanguage(bookDTO.getLanguage());
        book.setPrintLength(bookDTO.getPrintLength());

        bookRepository.save(book);
    }

    @Transactional
    public void addPublisherToBook(Long bookId, Long publisherId) {

        var publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found!"));

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found!"));


        book.setPublisher(publisher);

        bookRepository.save(book);
    }

    @Transactional
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
