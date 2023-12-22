package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.mapper.AuthorMapper;
import com.codexsoft.bookdatabank.mapper.BookMapper;
import com.codexsoft.bookdatabank.model.dto.*;
import com.codexsoft.bookdatabank.model.entity.Author;
import com.codexsoft.bookdatabank.model.entity.Book;
import com.codexsoft.bookdatabank.model.entity.Publisher;
import com.codexsoft.bookdatabank.repository.AuthorRepository;
import com.codexsoft.bookdatabank.repository.BookRepository;
import com.codexsoft.bookdatabank.repository.PublisherRepository;
import com.codexsoft.bookdatabank.repository.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    public Map<String, Object> getBooks(BookFilterDTO bookFilterDTO) {

        if(bookFilterDTO == null)
            return null;

        Specification<Book> specification = Specification.where(null);

        if(bookFilterDTO.getLanguage() != null && !bookFilterDTO.getLanguage().isEmpty()) {
            specification = specification.and(BookSpecification.hasLanguage(bookFilterDTO.getLanguage()));
        }

        if(bookFilterDTO.getAfterPublicationDate() != null) {
            specification = specification.and(BookSpecification.hasPublicationDateGreaterThanOrEqualTo(bookFilterDTO.getAfterPublicationDate()));
        }

        Page<Book> pageBook = bookRepository.findAll(specification, bookFilterDTO.getPageable());

        if(pageBook.getTotalElements() == 0)
            return null;

        List<BookDTO> bookDTOS = pageBook.map(bookMapper::map).getContent();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("books", bookDTOS);
        resultMap.put("currentPage", pageBook.getNumber());
        resultMap.put("totalItems", pageBook.getTotalElements());
        resultMap.put("totalPages", pageBook.getTotalPages());

        return resultMap;
    }

    public BookDTO getBook(Long bookId) {

        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(bookOptional.isEmpty())
            return null;

        Book book = bookOptional.get();

        return bookMapper.map(book);
    }

    public PublisherDTO getBookPublisher(Long bookId) {

        return bookRepository.findBookPublisher(bookId);
    }

    public List<AuthorDTO> getBookAuthors(Long bookId) {

        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(bookOptional.isEmpty())
            return null;

        Book book = bookOptional.get();

        List<Author> authors = book.getAuthors();

        return authorMapper.map(authors);
    }

    public BookDetailDTO getBookDetail(Long bookId) {

        return bookRepository.findBookDetail(bookId);
    }

    public Long createBook(BookDTO bookDTO) {

        Book book = bookMapper.map(bookDTO);

        Book newBook = bookRepository.save(book);

        return newBook.getBookId();
    }

    public Long updateBook(Long bookId, BookDTO bookDTO) {

        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(bookOptional.isEmpty())
            return 0L;

        Book book = bookOptional.get();
        book.setTitle(bookDTO.getTitle());
        book.setCoverImageUrl(bookDTO.getCoverImageUrl());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setLanguage(bookDTO.getLanguage());
        book.setPrintLength(bookDTO.getPrintLength());

        Book updatedBook = bookRepository.save(book);

        return updatedBook.getBookId();
    }

    public Long addPublisherToBook(Long bookId, Long publisherId) {

        Optional<Publisher> publisherOptional = publisherRepository.findById(publisherId);
        if (publisherOptional.isEmpty())
            return 0L;

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty())
            return 0L;

        Publisher publisher = publisherOptional.get();

        Book book = bookOptional.get();
        book.setPublisher(publisher);

        Book newBook = bookRepository.save(book);

        return newBook.getBookId();
    }

    public Long addAuthorToBook(Long bookId, Long authorId) {

        Optional<Author> authorOptional = authorRepository.findById(authorId);
        if(authorOptional.isEmpty())
            return 0L;

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty())
            return 0L;

        Author author = authorOptional.get();

        Book book = bookOptional.get();
        book.getAuthors().add(author);

        Book newBook = bookRepository.save(book);

        return newBook.getBookId();
    }
}
