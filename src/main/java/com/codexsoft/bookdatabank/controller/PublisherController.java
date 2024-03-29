package com.codexsoft.bookdatabank.controller;

import com.codexsoft.bookdatabank.mapper.PublisherMapper;
import com.codexsoft.bookdatabank.model.dto.PublisherBookDto;
import com.codexsoft.bookdatabank.model.dto.PublisherDto;
import com.codexsoft.bookdatabank.model.request.PublisherRequest;
import com.codexsoft.bookdatabank.service.PublisherService;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<List<PublisherDto>> getPublishers() {

        var publishers = publisherService.getPublishers();

        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<PublisherDto> getPublisher(@PathVariable Long publisherId) {

        return publisherService.getPublisher(publisherId)
                .map(publisher -> new ResponseEntity<>(publisher, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{publisherId}/publisher-books")
    public ResponseEntity<List<PublisherBookDto>> getPublisherBooks(@PathVariable Long publisherId) {

        var publisherBooks = publisherService.getPublisherBooks(publisherId);

        return new ResponseEntity<>(publisherBooks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createPublisher(@Valid @RequestBody PublisherRequest publisherRequest) {

        var publisherDto = publisherMapper.map(publisherRequest);

        Long response = publisherService.createPublisher(publisherDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{publisherId}")
    public ResponseEntity<Void> updatePublisher(@PathVariable Long publisherId, @Valid @RequestBody PublisherRequest publisherRequest) {

        var publisherDto = publisherMapper.map(publisherRequest);

        try {
            publisherService.updatePublisher(publisherId, publisherDto);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
