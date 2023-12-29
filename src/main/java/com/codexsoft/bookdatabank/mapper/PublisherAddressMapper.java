package com.codexsoft.bookdatabank.mapper;

import com.codexsoft.bookdatabank.model.dto.PublisherDto;
import com.codexsoft.bookdatabank.model.entity.PublisherAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherAddressMapper {
    PublisherAddress map(PublisherDto publisherDto);
}
