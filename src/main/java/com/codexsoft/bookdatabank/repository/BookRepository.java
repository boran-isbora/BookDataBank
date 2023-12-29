package com.codexsoft.bookdatabank.repository;

import com.codexsoft.bookdatabank.model.dto.AuthorDto;
import com.codexsoft.bookdatabank.model.dto.BookDetailDto;
import com.codexsoft.bookdatabank.model.dto.PublisherDto;
import com.codexsoft.bookdatabank.model.dto.ReportBookDto;
import com.codexsoft.bookdatabank.model.entity.Book;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query(value = """
            SELECT NEW com.codexsoft.bookdatabank.model.dto.PublisherDto(
                            p.id,
                            p.name,
                            a.address,
                            a.city,
                            a.country)
                  FROM Publisher p
                        LEFT OUTER JOIN PublisherAddress a
                            ON a.id = p.publisherAddress.id
                        INNER JOIN Book b
                            ON b.publisher.id = p.id
                 WHERE b.id = :bookId
            """)
    Optional<PublisherDto> findBookPublisher(Long bookId);


    @Query(value = """
            SELECT b.publisher.id AS publisherId,
                   b.publisher.name AS publisherName,
                   b.id AS bookId,
                   b.title AS bookTitle,
                   b.isbn AS isbn,
                   b.language AS language,
                   b.printLength AS printLength,
                   b.publicationDate AS publicationDate,
                   b.coverImageUrl AS coverImageUrl,
                   ba.id AS authorId,
                   ba.name AS authorName,
                   ba.surname AS authorSurname,
                   ba.about AS authorAbout
              FROM Book b
                    LEFT JOIN b.authors ba
                    LEFT JOIN b.publisher bp
             WHERE b.id = :bookId
        """)
    List<Tuple> findBookDetailInternal(Long bookId);

    default Optional<BookDetailDto> findBookDetail(Long bookId) {

        var records = findBookDetailInternal(bookId);

        if(records.isEmpty())
            return Optional.empty();

        var firstRecord = records.get(0);

        BookDetailDto result = new BookDetailDto();

        result.setPublisherId(firstRecord.get("publisherId", Long.class));
        result.setPublisherName(firstRecord.get("publisherName", String.class));
        result.setBookId(firstRecord.get("bookId", Long.class));
        result.setBookTitle(firstRecord.get("bookTitle", String.class));
        result.setIsbn(firstRecord.get("isbn", String.class));
        result.setLanguage(firstRecord.get("language", String.class));
        result.setPrintLength(firstRecord.get("printLength", Integer.class));
        result.setPublicationDate(firstRecord.get("publicationDate", LocalDate.class));
        result.setCoverImageUrl(firstRecord.get("coverImageUrl", String.class));

        List<AuthorDto> authors = records
                .stream()
                .map(t -> {
                    AuthorDto author = new AuthorDto();
                    author.setId(t.get("authorId", Long.class));
                    author.setName(t.get("authorName", String.class));
                    author.setSurname(t.get("authorSurname", String.class));
                    author.setAbout(t.get("authorAbout", String.class));
                    return author;
                })
                .toList();

        result.setAuthors(authors);

        return Optional.of(result);
    }


    @Query("""
            SELECT NEW com.codexsoft.bookdatabank.model.dto.ReportBookDto(
                            COALESCE(p.name, 'UNSPECIFIED'),
                            b.title,
                            b.language,
                            b.printLength,
                            b.publicationDate,
                            COALESCE(booksAuthors.authors, 'UNSPECIFIED'))
              FROM Book b
                LEFT JOIN Publisher p
                        ON p.id = b.publisher.id
                LEFT JOIN LATERAL (
                                    SELECT LISTAGG(ba.name || ' ' || ba.surname, ', ') AS authors
                                      FROM b.authors ba
                ) AS booksAuthors
            """)
    List<ReportBookDto> getAllBookReport();
}