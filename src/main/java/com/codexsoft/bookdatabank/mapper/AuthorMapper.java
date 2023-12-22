package com.codexsoft.bookdatabank.mapper;

import com.codexsoft.bookdatabank.model.dto.AuthorDTO;
import com.codexsoft.bookdatabank.model.entity.Author;
import com.codexsoft.bookdatabank.model.request.AuthorRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO map(AuthorRequest authorRequest);

    @Mapping(source = "authorId", target = "id")
    List<AuthorDTO> map(List<Author> authors);

    @Mapping(source = "authorId", target = "id")
    AuthorDTO map(Author author);

    @Mapping(source = "id", target = "authorId")
    Author map(AuthorDTO authorDTO);
}
