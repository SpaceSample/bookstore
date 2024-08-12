package com.alex.wang.bookstore.movie.repository;

import com.alex.wang.bookstore.movie.model.MovieRate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MovieRateRepository {
    private Map<Long, MovieRate> movieRateMap = new HashMap<>();

    public Optional<MovieRate> getMovieRate(Long id) {
        return Optional.ofNullable(movieRateMap.get(id));
    }

    public MovieRate createMovieRate(Long id, int rate) {
        MovieRate movieRate = new MovieRate(id, rate, 1);
        movieRateMap.put(id, movieRate);
        return movieRate;
    }

    public void updateMovieRate(MovieRate movieRate) {
        movieRateMap.put(movieRate.getId(), movieRate);
    }
}
