package com.alex.wang.bookstore.movie.repository;

import com.alex.wang.bookstore.movie.model.MovieRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieRateRepositoryTest {
    private MovieRateRepository movieRateRepository;

    @BeforeEach
    void setUp() {
        movieRateRepository = new MovieRateRepository();
    }

    @Test
    void getMovieRate() {
        assertTrue(movieRateRepository.getMovieRate(1L).isEmpty());
        movieRateRepository.createMovieRate(1L, 5);
        MovieRate movieRate = movieRateRepository.getMovieRate(1L).get();
        assertEquals(5.0, movieRate.getRate() );
        assertEquals(1, movieRate.getCount() );
    }

    @Test
    void createMovieRate() {
        assertTrue(movieRateRepository.getMovieRate(1L).isEmpty());
        movieRateRepository.createMovieRate(1L, 5);
        MovieRate movieRate = movieRateRepository.getMovieRate(1L).get();
        assertEquals(5.0, movieRate.getRate() );
        assertEquals(1, movieRate.getCount() );
    }

    @Test
    void updateMovieRate() {
        assertTrue(movieRateRepository.getMovieRate(1L).isEmpty());
        movieRateRepository.createMovieRate(1L, 5);
        MovieRate movieRate = movieRateRepository.getMovieRate(1L).get();
        assertEquals(5.0, movieRate.getRate() );
        assertEquals(1, movieRate.getCount() );
        movieRateRepository.updateMovieRate(new MovieRate(1L, 4.2, 2));
        movieRate = movieRateRepository.getMovieRate(1L).get();
        assertEquals(4.2, movieRate.getRate() );
        assertEquals(2, movieRate.getCount() );
    }
}