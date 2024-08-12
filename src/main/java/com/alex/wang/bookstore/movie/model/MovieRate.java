package com.alex.wang.bookstore.movie.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieRate {
    private long id;
    private double rate;
    private int count;
}
