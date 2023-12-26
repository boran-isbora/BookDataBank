package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.mapper.PublisherAddressMapper;
import com.codexsoft.bookdatabank.model.dto.PublisherBookDTO;
import com.codexsoft.bookdatabank.model.dto.PublisherDTO;
import com.codexsoft.bookdatabank.model.entity.Publisher;
import com.codexsoft.bookdatabank.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherAddressMapper publisherAddressMapper;

    public List<PublisherDTO> getPublishers() {

        return publisherRepository.findPublisher();
    }

    public Optional<PublisherDTO> getPublisher(Long publisherId) {

        return publisherRepository.findPublisher(publisherId);
    }

    public List<PublisherBookDTO> getPublisherBooks(Long publisherId) {

        return publisherRepository.findPublisherBooks(publisherId);
    }

    @Transactional
    public Long createPublisher(PublisherDTO publisherDTO) {

        var publisher = new Publisher();
        publisher.setName(publisherDTO.getName());

        if(publisherDTO.getAddress() != null || publisherDTO.getCity() != null || publisherDTO.getCountry() != null) {

            var publisherAddress = publisherAddressMapper.map(publisherDTO);

            publisher.setPublisherAddress(publisherAddress);
        }

        publisherRepository.save(publisher);

        return publisher.getId();
    }

    @Transactional
    public void updatePublisher(Long publisherId, PublisherDTO publisherDTO) {

        var publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found!"));

        publisher.setName(publisherDTO.getName());

        if(publisher.getPublisherAddress() != null) {

            var publisherAddress = publisher.getPublisherAddress();

            publisherAddress.setAddress(publisherDTO.getAddress());
            publisherAddress.setCity(publisherDTO.getCity());
            publisherAddress.setCountry(publisherDTO.getCountry());

        } else if(publisherDTO.getAddress() != null || publisherDTO.getCity() != null || publisherDTO.getCountry() != null) {

            var newPublisherAddress =  publisherAddressMapper.map(publisherDTO);
            publisher.setPublisherAddress(newPublisherAddress);
        }

        publisherRepository.save(publisher);
    }
}
