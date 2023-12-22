package com.codexsoft.bookdatabank.mapper;

import com.codexsoft.bookdatabank.model.dto.BookDTO;
import com.codexsoft.bookdatabank.model.entity.Book;
import com.codexsoft.bookdatabank.model.request.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO map(BookRequest bookRequest);

    @Mapping(source = "bookId", target = "id")
    BookDTO map(Book book);

    @Mapping(source = "id", target = "bookId")
    Book map(BookDTO bookDTO);
}
