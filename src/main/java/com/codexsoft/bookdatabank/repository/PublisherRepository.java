package com.codexsoft.bookdatabank.repository;

import com.codexsoft.bookdatabank.model.dto.PublisherBookDTO;
import com.codexsoft.bookdatabank.model.dto.PublisherDTO;
import com.codexsoft.bookdatabank.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("""
            SELECT NEW com.codexsoft.bookdatabank.model.dto.PublisherDTO(
                            p.publisherId,
                            p.name,
                            a.address,
                            a.city,
                            a.country)
            FROM Publisher p
                 LEFT OUTER JOIN PublisherAddress a 
                        ON a.publisherAddressId = p.publisherAddress.publisherAddressId
    """)
    List<PublisherDTO> findPublisher();

    @Query("""
            SELECT NEW com.codexsoft.bookdatabank.model.dto.PublisherDTO(
                            p.publisherId,
                            p.name,
                            a.address,
                            a.city,
                            a.country)
              FROM Publisher p
                LEFT OUTER JOIN PublisherAddress a
                        ON a.publisherAddressId = p.publisherAddress.publisherAddressId
             WHERE p.publisherId = :publisherId
    """)
    PublisherDTO findPublisher(Long publisherId);

    @Query("""
            SELECT NEW com.codexsoft.bookdatabank.model.dto.PublisherBookDTO(
                            p.publisherId,
                            p.name,
                            b.bookId,
                            b.title,
                            b.coverImageUrl)
              FROM Publisher p
                INNER JOIN Book b
                        ON b.publisher.publisherId = p.publisherId
             WHERE p.publisherId = :publisherId
    """)
    List<PublisherBookDTO> findPublisherBooks(Long publisherId);
}
