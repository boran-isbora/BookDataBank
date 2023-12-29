package com.codexsoft.bookdatabank.mapper;

import com.codexsoft.bookdatabank.model.dto.PublisherDto;
import com.codexsoft.bookdatabank.model.request.PublisherRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    PublisherDto map(PublisherRequest publisherRequest);
}
