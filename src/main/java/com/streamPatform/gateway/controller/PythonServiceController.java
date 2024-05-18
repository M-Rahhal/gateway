package com.streamPatform.gateway.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.streamPatform.gateway.utils.annotations.PythonServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.streamPatform.gateway.services.FilteredMovieService;

@RestController
public class PythonServiceController {

    @Autowired
    private FilteredMovieService service;
    @PythonServiceAPI
    @PostMapping("/filtering/add-movie")
    public ResponseEntity addFilteredMovie(@JsonProperty("movie-name") String movieName,
                                           @JsonProperty("movie-path") String moviePath){
        service.createFilteredMovie(movieName , moviePath);
        return ResponseEntity.ok().build();
    }
}
