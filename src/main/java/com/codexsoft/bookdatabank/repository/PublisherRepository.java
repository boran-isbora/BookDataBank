package com.codexsoft.bookdatabank.repository;

import com.codexsoft.bookdatabank.model.dto.PublisherBookDto;
import com.codexsoft.bookdatabank.model.dto.PublisherDto;
import com.codexsoft.bookdatabank.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("""
            SELECT NEW com.codexsoft.bookdatabank.model.dto.PublisherDto(
                            p.id,
                            p.name,
                            a.address,
                            a.city,
                            a.country)
            FROM Publisher p
                 LEFT OUTER JOIN PublisherAddress a
                        ON a.id = p.publisherAddress.id
    """)
    List<PublisherDto> findPublisher();

    @Query("""
            SELECT NEW com.codexsoft.bookdatabank.model.dto.PublisherDto(
                            p.id,
                            p.name,
                            a.address,
                            a.city,
                            a.country)
              FROM Publisher p
                LEFT OUTER JOIN PublisherAddress a
                        ON a.id = p.publisherAddress.id
             WHERE p.id = :publisherId
    """)
    Optional<PublisherDto> findPublisher(Long publisherId);

    @Query("""
            SELECT NEW com.codexsoft.bookdatabank.model.dto.PublisherBookDto(
                            p.id,
                            p.name,
                            b.id,
                            b.title,
                            b.coverImageUrl)
              FROM Publisher p
                INNER JOIN Book b
                        ON b.publisher.id = p.id
             WHERE p.id = :publisherId
    """)
    List<PublisherBookDto> findPublisherBooks(Long publisherId);
}
