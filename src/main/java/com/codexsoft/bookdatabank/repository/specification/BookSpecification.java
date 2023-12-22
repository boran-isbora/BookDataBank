package com.codexsoft.bookdatabank.repository.specification;

import com.codexsoft.bookdatabank.model.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookSpecification {
    public static Specification<Book> hasLanguage(String language) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("language"), language);
    }

    public static Specification<Book> hasPublicationDateGreaterThanOrEqualTo(LocalDate publicationDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("publicationDate"), publicationDate);
    }
}
