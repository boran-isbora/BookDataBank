package com.codexsoft.bookdatabank.repository;

import com.codexsoft.bookdatabank.model.dto.AuthorDTO;
import com.codexsoft.bookdatabank.model.dto.BookDetailDTO;
import com.codexsoft.bookdatabank.model.dto.PublisherDTO;
import com.codexsoft.bookdatabank.model.dto.ReportBookDTO;
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
            SELECT NEW com.codexsoft.bookdatabank.model.dto.PublisherDTO(
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
    Optional<PublisherDTO> findBookPublisher(Long bookId);


    @Query(value = """
            SELECT p.id AS publisherId,
                   p.name AS publisherName,
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
                    LEFT JOIN Publisher p
                            ON p.id = b.publisher.id
             WHERE b.id = :bookId
        """)
    List<Tuple> findBookDetailInternal(Long bookId);


    default Optional<BookDetailDTO> findBookDetail(Long bookId) {

        var records = findBookDetailInternal(bookId);

        Optional<BookDetailDTO> result = Optional.of(new BookDetailDTO());

        if(records.isEmpty())
            return result;

        var firstRecord = records.get(0);
        result.get().setPublisherId(firstRecord.get("publisherId", Long.class));
        result.get().setPublisherName(firstRecord.get("publisherName", String.class));
        result.get().setBookId(firstRecord.get("bookId", Long.class));
        result.get().setBookTitle(firstRecord.get("bookTitle", String.class));
        result.get().setIsbn(firstRecord.get("isbn", String.class));
        result.get().setLanguage(firstRecord.get("language", String.class));
        result.get().setPrintLength(firstRecord.get("printLength", Integer.class));
        result.get().setPublicationDate(firstRecord.get("publicationDate", LocalDate.class));
        result.get().setCoverImageUrl(firstRecord.get("coverImageUrl", String.class));

        List<AuthorDTO> authors = records.stream().map(t -> {
            AuthorDTO author = new AuthorDTO();
            author.setId(t.get("authorId", Long.class));
            author.setName(t.get("authorName", String.class));
            author.setSurname(t.get("authorSurname", String.class));
            author.setAbout(t.get("authorAbout", String.class));
            return author;
        }).toList();

        result.get().setAuthors(authors);

        return result;
    }


    @Query("""
            SELECT NEW com.codexsoft.bookdatabank.model.dto.ReportBookDTO(
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
    List<ReportBookDTO> getAllBookReport();
}