package com.codexsoft.bookdatabank.controller;

import com.codexsoft.bookdatabank.mapper.PublisherMapper;
import com.codexsoft.bookdatabank.model.dto.PublisherBookDTO;
import com.codexsoft.bookdatabank.model.dto.PublisherDTO;
import com.codexsoft.bookdatabank.model.request.PublisherRequest;
import com.codexsoft.bookdatabank.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;

    @GetMapping
    public ResponseEntity<List<PublisherDTO>> getPublishers() {

        List<PublisherDTO> publishers = publisherService.getPublishers();

        if(publishers == null || publishers.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<PublisherDTO> getPublisher(@PathVariable Long publisherId) {

        PublisherDTO publisher = publisherService.getPublisher(publisherId);

        if(publisher == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @GetMapping("/{publisherId}/publisher-books")
    public ResponseEntity<List<PublisherBookDTO>> getPublisherBooks(@PathVariable Long publisherId) {

        List<PublisherBookDTO> publisherBooks = publisherService.getPublisherBooks(publisherId);

        if(publisherBooks == null || publisherBooks.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(publisherBooks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createPublisher(@Valid @RequestBody PublisherRequest publisherRequest) {

        PublisherDTO publisherDTO = publisherMapper.map(publisherRequest);

        Long response = publisherService.createPublisher(publisherDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{publisherId}")
    public ResponseEntity<HttpStatus> updatePublisher(@PathVariable Long publisherId, @Valid @RequestBody PublisherRequest publisherRequest) {

        PublisherDTO publisherDTO = publisherMapper.map(publisherRequest);

        Long updatedPublisherId = publisherService.updatePublisher(publisherId, publisherDTO);

        if(updatedPublisherId == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
