package com.codexsoft.bookdatabank.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PublisherRequest {

    @NotNull(message = "NAME should not be null")
    @NotEmpty(message = "NAME should not be empty")
    private String name;

    @NotEmpty(message = "ADDRESS should not be empty")
    private String address;

    @NotEmpty(message = "CITY should not be empty")
    private String city;

    @NotEmpty(message = "COUNTRY should not be empty")
    private String country;
}
