package com.alex.wang.bookstore.movie.service;

import com.alex.wang.bookstore.movie.dto.MovieDto;
import com.alex.wang.bookstore.movie.dto.MovieQueryResDto;
import com.alex.wang.bookstore.movie.dto.MovieSummaryDto;
import com.alex.wang.bookstore.movie.dto.RateResDto;
import com.alex.wang.bookstore.movie.model.Movie;
import com.alex.wang.bookstore.movie.model.MovieRate;
import com.alex.wang.bookstore.movie.repository.MovieRateRepository;
import com.alex.wang.bookstore.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieRateRepository movieRateRepository;
    private static final int PAGE_SIZE = 10;

    private int getTotalPages (int resultSize) {
        return (int)Math.ceil(resultSize * 1.0 / PAGE_SIZE);
    }

    public MovieQueryResDto queryMovies(Optional<String> title, int pageNum) {
        if(pageNum < 1){
            throw new IllegalArgumentException("Page number should start from 1.");
        }
        List<Movie> movieList = movieRepository.findAll(title);
        int totalPages = getTotalPages(movieList.size());
        int page = totalPages < pageNum ? 1 : pageNum;
        List<MovieSummaryDto> resList = movieList.stream()
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .map(this::movieToSummaryDto)
                .collect(Collectors.toList());
        return new MovieQueryResDto(resList, page, totalPages);
    }

    public Optional<MovieDto> getMovieById(Long id) {
        return movieRepository.findById(id).map(this::movieToDto);
    }

    private MovieDto movieToDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getYear(),
                movie.getRuntime(),
                movie.getGenres(),
                movie.getDirector(),
                movie.getActors(),
                movie.getPlot(),
                movie.getPosterUrl(),
                movieRateRepository.getMovieRate(movie.getId())
                        .map(movieRate -> new RateResDto(movieRate.getRate(), movieRate.getCount()))
                        .orElseGet(() -> new RateResDto(0.0, 0))
        );
    }

    private MovieSummaryDto movieToSummaryDto(Movie movie) {
        return new MovieSummaryDto(
                movie.getId(),
                movie.getTitle(),
                movie.getPosterUrl(),
                movieRateRepository.getMovieRate(movie.getId())
                        .map(movieRate -> new RateResDto(movieRate.getRate(), movieRate.getCount()))
                        .orElseGet(() -> new RateResDto(0.0, 0))
        );
    }

    public RateResDto rateMovie(Long id, int rate) {
        return movieRateRepository.getMovieRate(id)
                .map(movieRate -> {
                    int newCount = movieRate.getCount() + 1;
                    movieRate.setRate((movieRate.getRate() * movieRate.getCount() + rate) / newCount);
                    movieRate.setCount(newCount);
                    movieRateRepository.updateMovieRate(movieRate);
                    return new RateResDto(movieRate.getRate(), movieRate.getCount());
                }).orElseGet(() -> {
                    MovieRate movieRate = movieRateRepository.createMovieRate(id, rate);
                    return new RateResDto(movieRate.getRate(), movieRate.getCount());
                });
    }
}
