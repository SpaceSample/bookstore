package com.alex.wang.bookstore.movie.controller;

import com.alex.wang.bookstore.movie.dto.MovieDto;
import com.alex.wang.bookstore.movie.dto.MovieQueryResDto;
import com.alex.wang.bookstore.movie.dto.RateReqDto;
import com.alex.wang.bookstore.movie.dto.RateResDto;
import com.alex.wang.bookstore.movie.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin
public class MovieController {
    @Autowired
    private MovieService movieService;
    @GetMapping("")
    @Operation(summary = "Query movies", description = "Query movie list by title(optional), with pagination")
    @ApiResponse(responseCode = "200", description = "Query successful")
    public ResponseEntity<MovieQueryResDto> queryMovies(@RequestParam(required = false) String title, @RequestParam(required = false, defaultValue = "1") int pageNum) {
        return ResponseEntity.ok(movieService.queryMovies(Optional.ofNullable(title), pageNum));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Query single movie", description = "Query one movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully find the movie"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return ResponseEntity.of(movieService.getMovieById(id));
    }

    @PutMapping("/{id}/rate")
    @Operation(summary = "Submit rating", description = "User submit rating of the movie")
    @ApiResponse(responseCode = "200", description = "Submit successful, return the new rating data")
    public ResponseEntity<RateResDto> rateMovie(@PathVariable Long id, @RequestBody RateReqDto rateReq) {
        return ResponseEntity.ok(movieService.rateMovie(id, rateReq.getRate()));
    }
}
