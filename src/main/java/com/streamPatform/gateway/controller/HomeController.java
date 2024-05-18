package com.streamPatform.gateway.controller;

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
    private MovieService service;
    @PublicAPI
    @GetMapping("/movies/all-movies")
    public ResponseEntity getAllMovies(){
        return ResponseEntity.ok().body(service.getAllMovies());
    }


    @PublicAPI
    @GetMapping("/movies/{id}")
    public ResponseEntity getMovieByID(@PathVariable Long id){
        return ResponseEntity.ok().body(service.getById(id));
    }
}
