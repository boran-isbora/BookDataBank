package com.codexsoft.bookdatabank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PublisherDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
}
