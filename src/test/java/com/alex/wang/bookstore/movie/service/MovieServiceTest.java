package com.alex.wang.bookstore.movie.service;

import com.alex.wang.bookstore.movie.dto.MovieDto;
import com.alex.wang.bookstore.movie.dto.RateResDto;
import com.alex.wang.bookstore.movie.model.Movie;
import com.alex.wang.bookstore.movie.model.MovieRate;
import com.alex.wang.bookstore.movie.repository.MovieRateRepository;
import com.alex.wang.bookstore.movie.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import com.alex.wang.bookstore.movie.dto.MovieQueryResDto;

@WebMvcTest(MovieService.class)
class MovieServiceTest {
    @MockBean
    private MovieRepository movieRepository;
    @MockBean
    private MovieRateRepository movieRateRepository;

    @Autowired
    private MovieService movieService;

    @Test
    void queryMovies(){
        List<Movie> movieList = new ArrayList<>();
        for(int i=0; i<15; i++){
            Movie m = new Movie();
            m.setId(i);
            m.setTitle("m"+i);
            movieList.add(m);
        }
        Mockito.when(movieRepository.findAll(Optional.empty())).thenReturn(movieList);
        MovieQueryResDto movieQueryResDto = movieService.queryMovies(Optional.empty(), 1);
        assertEquals(10, movieQueryResDto.getMovies().size());
        assertEquals(1, movieQueryResDto.getPage());
        assertEquals(2, movieQueryResDto.getTotalPages());

        movieQueryResDto = movieService.queryMovies(Optional.empty(), 2);
        assertEquals(5, movieQueryResDto.getMovies().size());
        assertEquals(2, movieQueryResDto.getPage());
        assertEquals(2, movieQueryResDto.getTotalPages());

        // not have page 3, use 1
        movieQueryResDto = movieService.queryMovies(Optional.empty(), 3);
        assertEquals(10, movieQueryResDto.getMovies().size());
        assertEquals(1, movieQueryResDto.getPage());
        assertEquals(2, movieQueryResDto.getTotalPages());
    }

    @Test
    void getMovieById(){
        Movie m = new Movie();
        m.setId(1);
        m.setTitle("m1");
        // found
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(m));
        Optional<MovieDto> movieDto = movieService.getMovieById(1L);
        assertFalse(movieDto.isEmpty());
        assertEquals(1L, movieDto.get().getId());
        // not found
        Mockito.when(movieRepository.findById(2L)).thenReturn(Optional.empty());
        movieDto = movieService.getMovieById(2L);
        assertTrue(movieDto.isEmpty());
    }

    @Test
    void rateMovie(){
        MovieRate movieRate = new MovieRate(1L, 3, 1);
        Mockito.when(movieRateRepository.getMovieRate(1L)).thenReturn(Optional.of(movieRate));
        RateResDto rateResDto = movieService.rateMovie(1L, 4);
        assertEquals(3.5, rateResDto.getRate());
        assertEquals(2, rateResDto.getCount());

        Mockito.when(movieRateRepository.getMovieRate(2L)).thenReturn(Optional.empty());
        Mockito.when(movieRateRepository.createMovieRate(2L, 4)).thenReturn(new MovieRate(2L, 4.0, 1));
        rateResDto = movieService.rateMovie(2L, 4);
        assertEquals(4.0, rateResDto.getRate());
        assertEquals(1, rateResDto.getCount());
    }
}