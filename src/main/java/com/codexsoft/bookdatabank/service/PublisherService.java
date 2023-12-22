package com.codexsoft.bookdatabank.service;

import com.codexsoft.bookdatabank.mapper.PublisherAddressMapper;
import com.codexsoft.bookdatabank.model.dto.PublisherBookDTO;
import com.codexsoft.bookdatabank.model.dto.PublisherDTO;
import com.codexsoft.bookdatabank.model.entity.Publisher;
import com.codexsoft.bookdatabank.model.entity.PublisherAddress;
import com.codexsoft.bookdatabank.repository.PublisherRepository;
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

    public PublisherDTO getPublisher(Long publisherId) {

        return publisherRepository.findPublisher(publisherId);
    }

    public List<PublisherBookDTO> getPublisherBooks(Long publisherId) {

        return publisherRepository.findPublisherBooks(publisherId);
    }

    public Long createPublisher(PublisherDTO publisherDTO) {

        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());

        if((publisherDTO.getAddress() != null && !publisherDTO.getAddress().isBlank()) ||
                (publisherDTO.getCity() != null &&  !publisherDTO.getCity().isBlank()) ||
                (publisherDTO.getCountry() != null &&  !publisherDTO.getCountry().isBlank())) {

            PublisherAddress publisherAddress = publisherAddressMapper.map(publisherDTO);

            publisher.setPublisherAddress(publisherAddress);
        }

        Publisher newnewPublisher = publisherRepository.save(publisher);

        return newnewPublisher.getPublisherId();
    }

    public Long updatePublisher(Long publisherId, PublisherDTO publisherDTO) {

        Optional<Publisher> publisherOptional = publisherRepository.findById(publisherId);

        if(publisherOptional.isEmpty())
            return 0L;

        Publisher publisher = publisherOptional.get();
        publisher.setName(publisherDTO.getName());

        if(publisher.getPublisherAddress() != null) {

            publisher.getPublisherAddress().setAddress(publisherDTO.getAddress());
            publisher.getPublisherAddress().setCity(publisherDTO.getCity());
            publisher.getPublisherAddress().setCountry(publisherDTO.getCountry());

        } else if(!publisherDTO.getAddress().isBlank() ||
                !publisherDTO.getCity().isBlank() ||
                !publisherDTO.getCountry().isBlank()) {

            PublisherAddress newPublisherAddress =  publisherAddressMapper.map(publisherDTO);
            publisher.setPublisherAddress(newPublisherAddress);
        }

        Publisher updatedPublisher = publisherRepository.save(publisher);

        return updatedPublisher.getPublisherId();
    }
}
