package com.alex.wang.bookstore.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieQueryResDto {
    private List<MovieSummaryDto> movies;
    private int page;
    private int totalPages;
}
