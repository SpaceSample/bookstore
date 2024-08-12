package com.alex.wang.bookstore.movie.repository;

import com.alex.wang.bookstore.movie.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {
    private Map<Long, Movie> movieMap = new HashMap<>();
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ObjectMapper objectMapper;

    public List<Movie> findAll(Optional<String> title) {
        return movieMap.values().stream()
                .filter(movie -> title.isEmpty() || movie.getTitle().contains(title.get()))
                .collect(Collectors.toList());
    }

    public Optional<Movie> findById(Long id) {
        return Optional.ofNullable(movieMap.get(id));
    }

    @PostConstruct
    public void init() {
        Resource resource = resourceLoader.getResource("classpath:movies.json");

        try {
            Arrays.stream(objectMapper.readValue(resource.getInputStream(), Movie[].class))
                    .forEach(movie -> movieMap.put(movie.getId(), movie));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
