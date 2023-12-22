package com.codexsoft.bookdatabank.mapper;

import com.codexsoft.bookdatabank.model.dto.PublisherDTO;
import com.codexsoft.bookdatabank.model.request.PublisherRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    PublisherDTO map(PublisherRequest publisherRequest);
}
