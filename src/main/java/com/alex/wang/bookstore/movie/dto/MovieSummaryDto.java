package com.alex.wang.bookstore.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieSummaryDto {
    private long id;
    private String title;
    private String posterUrl;
    private RateResDto rate;
}
