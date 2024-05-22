package com.streamPatform.gateway.controller;

import com.streamPatform.gateway.services.FilteredMovieService;
import com.streamPatform.gateway.utils.annotations.PublicAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.streamPatform.gateway.services.MovieService;

@RestController
public class HomeController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private FilteredMovieService filteredMovieService;
    @PublicAPI
    @GetMapping("/movies/all-movies")
    public ResponseEntity getAllMovies(){
        return ResponseEntity.ok().body(movieService.getAllMovies());
    }

    @PublicAPI
    @GetMapping("/filtered/all-filtered")
    public ResponseEntity getAllFiltered(){
        return ResponseEntity.ok().body(filteredMovieService.getAllFiltered());
    }


    @PublicAPI
    @GetMapping("/movies/{id}")
    public ResponseEntity getMovieByID(@PathVariable Long id){
        return ResponseEntity.ok().body(movieService.getById(id));
    }
}
