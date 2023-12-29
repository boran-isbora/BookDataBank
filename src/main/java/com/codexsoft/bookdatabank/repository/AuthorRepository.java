package com.codexsoft.bookdatabank.repository;

import com.codexsoft.bookdatabank.model.dto.AuthorBookDto;
import com.codexsoft.bookdatabank.model.entity.Author;
import com.codexsoft.bookdatabank.repository.customized.CustomizedAuthorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>, CustomizedAuthorRepository {
    @Query("""
            SELECT NEW com.codexsoft.bookdatabank.model.dto.AuthorBookDto(
                            x.id,
                            x.name,
                            x.surname,
                            b.id,
                            b.title,
                            b.coverImageUrl)
              FROM Book b
                    INNER JOIN b.authors x
             WHERE x.id = :authorId
    """)
    List<AuthorBookDto> findAuthorBooks(Long authorId);
}
