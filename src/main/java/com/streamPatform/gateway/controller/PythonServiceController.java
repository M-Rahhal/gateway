package com.streamPatform.gateway.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.streamPatform.gateway.requests.SaveFilteredMovieRequest;
import com.streamPatform.gateway.utils.annotations.PythonServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.streamPatform.gateway.services.FilteredMovieService;

@RestController
public class PythonServiceController {

    @Autowired
    private FilteredMovieService service;
    @PythonServiceAPI
    @PostMapping("/filtering/add-movie")
    public ResponseEntity addFilteredMovie(@RequestBody SaveFilteredMovieRequest request) throws Exception {
        service.createFilteredMovie(request.movieName());
        return ResponseEntity.ok().build();
    }
}
