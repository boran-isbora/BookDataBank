package com.codexsoft.bookdatabank.mapper;

import com.codexsoft.bookdatabank.model.dto.BookDTO;
import com.codexsoft.bookdatabank.model.entity.Book;
import com.codexsoft.bookdatabank.model.request.BookRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO map(BookRequest bookRequest);
    BookDTO map(Book book);
    Book map(BookDTO bookDTO);
}
