package com.codexsoft.bookdatabank.repository.customized;

import com.codexsoft.bookdatabank.model.entity.Author;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomizedAuthorRepositoryImpl implements CustomizedAuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Author> getAuthors(Integer pageNumber, Integer pageSize) {

        return entityManager.createQuery("SELECT a FROM Author a", Author.class)
                .setFirstResult((pageNumber) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
