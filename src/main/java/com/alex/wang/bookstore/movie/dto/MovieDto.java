package com.alex.wang.bookstore.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDto {
    private long id;
    private String title;
    private String year;
    private String runtime;
    private String[] genres;
    private String director;
    private String actors;
    private String plot;
    private String posterUrl;
    private RateResDto rate;
}
