package com.codexsoft.bookdatabank.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageDto<T> {
    private Long totalItems;
    private Integer currentPage;
    private Integer totalPages;
    private List<T> list;
}
