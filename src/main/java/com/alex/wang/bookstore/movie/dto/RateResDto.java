package com.alex.wang.bookstore.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateResDto {
    private double rate;
    private int count;
}
