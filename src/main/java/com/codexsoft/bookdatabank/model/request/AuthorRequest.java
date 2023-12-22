package com.codexsoft.bookdatabank.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthorRequest {

    @NotNull(message = "NAME should not be null")
    @NotEmpty(message = "NAME should not be empty")
    private String name;

    @NotNull(message = "SURNAME should not be null")
    @NotEmpty(message = "SURNAME should not be empty")
    private String surname;
    private String about;
}
