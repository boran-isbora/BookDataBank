package com.codexsoft.bookdatabank.repository.customized;

import com.codexsoft.bookdatabank.model.entity.Author;

import java.util.List;

public interface CustomizedAuthorRepository {
    List<Author> getAuthors(Integer pageNumber, Integer pageSize);
}
