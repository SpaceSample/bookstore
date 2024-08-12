package com.alex.wang.bookstore.movie.controller;

import com.alex.wang.bookstore.movie.dto.MovieDto;
import com.alex.wang.bookstore.movie.dto.MovieQueryResDto;
import com.alex.wang.bookstore.movie.dto.RateReqDto;
import com.alex.wang.bookstore.movie.dto.RateResDto;
import com.alex.wang.bookstore.movie.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.http.MediaType;
@WebMvcTest(controllers = MovieController.class)
class MovieControllerTest {
    @MockBean
    private MovieService movieService;

    @Autowired
    private MovieController movieController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void queryMovies() throws Exception {
        MovieQueryResDto movieQueryResDto = new MovieQueryResDto(new ArrayList<>(), 1, 1);
        Mockito.when(movieService.queryMovies(Optional.empty(), 1)).thenReturn(movieQueryResDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"movies\":[], \"page\":1, \"totalPages\":1}"));
    }

    @Test
    void getMovieById() throws Exception {
        MovieDto movieDto = new MovieDto(1L,
                "title", "2024", "100",
                new String[]{"History"}, "director",
                "actors", "plot", "url", new RateResDto(3.0, 1));
        Mockito.when(movieService.getMovieById(1L)).thenReturn(Optional.of(movieDto));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1, \"title\":\"title\", \"year\":\"2024\", \"runtime\":\"100\"," +
                        "\"genres\":[\"History\"], \"director\":\"director\"," +
                        "\"actors\":\"actors\", \"plot\":\"plot\", \"posterUrl\":\"url\",\"rate\":{\"rate\":3.0, \"count\":1}}"));
    }

    @Test
    void rateMovie() throws Exception {
        RateResDto rateResDto = new RateResDto(3.0, 2);
        RateReqDto rateReq = new RateReqDto(3);
        Mockito.when(movieService.rateMovie(Mockito.anyLong(), Mockito.anyInt())).thenReturn(rateResDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/movies/1/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"rate\":3.0, \"count\":1}"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("{\"rate\":3.0, \"count\":2}"));
    }
}