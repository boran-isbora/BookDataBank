package com.codexsoft.bookdatabank.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PublisherRequest {

    @NotNull(message = "NAME should not be null")
    @NotEmpty(message = "NAME should not be empty")
    private String name;

    private String address;
    private String city;
    private String country;
}
