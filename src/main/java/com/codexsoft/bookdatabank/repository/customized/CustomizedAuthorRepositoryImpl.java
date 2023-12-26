package com.codexsoft.bookdatabank.repository.customized;

import com.codexsoft.bookdatabank.model.entity.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomizedAuthorRepositoryImpl implements CustomizedAuthorRepository {

    private final EntityManager entityManager;

    public List<Author> getAuthors(Integer pageNumber, Integer pageSize) {

        TypedQuery<Author> query = entityManager.createQuery("SELECT a FROM Author a", Author.class);
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
}
