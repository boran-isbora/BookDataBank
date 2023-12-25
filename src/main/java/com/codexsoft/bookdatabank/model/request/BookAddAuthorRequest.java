package com.codexsoft.bookdatabank.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookAddAuthorRequest {

    @NotNull(message = "ID should not be null")
    private Long authorId;
}
