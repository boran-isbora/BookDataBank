package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.mapper.PublisherAddressMapper;
import com.codexsoft.bookdatabank.model.dto.PublisherBookDto;
import com.codexsoft.bookdatabank.model.dto.PublisherDto;
import com.codexsoft.bookdatabank.model.entity.Publisher;
import com.codexsoft.bookdatabank.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherAddressMapper publisherAddressMapper;

    public List<PublisherDto> getPublishers() {

        return publisherRepository.findPublisher();
    }

    public Optional<PublisherDto> getPublisher(Long publisherId) {

        return publisherRepository.findPublisher(publisherId);
    }

    public List<PublisherBookDto> getPublisherBooks(Long publisherId) {

        return publisherRepository.findPublisherBooks(publisherId);
    }


    public Long createPublisher(PublisherDto publisherDto) {

        var publisher = new Publisher();
        publisher.setName(publisherDto.getName());

        if(publisherDto.getAddress() != null || publisherDto.getCity() != null || publisherDto.getCountry() != null) {

            var publisherAddress = publisherAddressMapper.map(publisherDto);

            publisher.setPublisherAddress(publisherAddress);
        }

        publisherRepository.save(publisher);

        return publisher.getId();
    }


    public void updatePublisher(Long publisherId, PublisherDto publisherDto) {

        var publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found!"));

        publisher.setName(publisherDto.getName());

        if(publisher.getPublisherAddress() != null) {

            var address = publisher.getPublisherAddress();

            address.setAddress(publisherDto.getAddress());
            address.setCity(publisherDto.getCity());
            address.setCountry(publisherDto.getCountry());

        } else if(publisherDto.getAddress() != null || publisherDto.getCity() != null || publisherDto.getCountry() != null) {

            var address =  publisherAddressMapper.map(publisherDto);
            publisher.setPublisherAddress(address);
        }

        publisherRepository.save(publisher);
    }
}
