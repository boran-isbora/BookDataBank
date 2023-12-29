package com.codexsoft.bookdatabank.mapper;

import com.codexsoft.bookdatabank.model.dto.BookDto;
import com.codexsoft.bookdatabank.model.entity.Book;
import com.codexsoft.bookdatabank.model.request.BookRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto map(BookRequest bookRequest);
    BookDto map(Book book);
    Book map(BookDto bookDto);
}
