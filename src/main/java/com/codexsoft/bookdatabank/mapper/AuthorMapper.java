package com.codexsoft.bookdatabank.mapper;

import com.codexsoft.bookdatabank.model.dto.AuthorDto;
import com.codexsoft.bookdatabank.model.entity.Author;
import com.codexsoft.bookdatabank.model.request.AuthorRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto map(AuthorRequest authorRequest);
    List<AuthorDto> map(List<Author> authors);
    AuthorDto map(Author author);
    Author map(AuthorDto authorDto);
}
