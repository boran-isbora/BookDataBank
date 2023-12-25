package com.codexsoft.bookdatabank.mapper;

import com.codexsoft.bookdatabank.model.dto.AuthorDTO;
import com.codexsoft.bookdatabank.model.entity.Author;
import com.codexsoft.bookdatabank.model.request.AuthorRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO map(AuthorRequest authorRequest);
    List<AuthorDTO> map(List<Author> authors);
    AuthorDTO map(Author author);
    Author map(AuthorDTO authorDTO);
}
