package com.alex.wang.bookstore.movie.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void findAll() {
        assertTrue(movieRepository.findAll(Optional.empty()).size() > 0);
        assertTrue(movieRepository.findAll(Optional.of("s")).size() > 0);
    }

    @Test
    void findById() {
        assertNotNull(movieRepository.findById(1L));
    }

    @Test
    void init() {
        assertTrue(movieRepository.findAll(Optional.empty()).size() > 0);
    }
}