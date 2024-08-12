package com.alex.wang.bookstore.movie.model;

import lombok.Data;

@Data
public class Movie {
    private long id;
    private String title;
    private String year;
    private String runtime;
    private String[] genres;
    private String director;
    private String actors;
    private String plot;
    private String posterUrl;
}
