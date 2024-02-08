package com.codexsoft.bookdatabank.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookAddPublisherRequest {

    @NotNull(message = "ID should not be null")
    private Long publisherId;
}
