package com.streamPatform.gateway.controller;

import com.streamPatform.gateway.entity.FilteredMovie;
import com.streamPatform.gateway.services.FilteredMovieService;
import com.streamPatform.gateway.utils.annotations.AdminAPI;
import com.streamPatform.gateway.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.streamPatform.gateway.services.MovieService;
import com.streamPatform.gateway.services.StorageService;

import java.io.IOException;
import java.util.Base64;

@RestController
public class AdminController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private FilteredMovieService filteredMovieService;

    @AdminAPI
    @PostMapping("/upload")
    public ResponseEntity uploadVideo(@RequestParam("video")MultipartFile file  ,
                                      @RequestParam("video-name")String name ,
                                      @RequestParam("filtered") String filtered ,
                                      @RequestParam("do-filter") String doFilter) throws IOException {
        if (file.isEmpty())
            return ResponseEntity.badRequest().body("The file is Empty");

        name = name+".mp4";
        byte[] bytes =  file.getBytes();
        String type = file.getContentType();
        if (!type.equals("video/mp4"))
            return ResponseEntity.badRequest().body("Unsupported format");

        if (filtered.equals("true")){

            try {
                filteredMovieService.createFilteredMovie(name);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            FilteredMovie movie = filteredMovieService.getByName(name);
            String path = null;
            try {
                path = storageService.saveFilteredVideo(file ,movie.getMovieName());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            movie.setMoviePath(path);
            filteredMovieService.saveMovie(movie);
            return ResponseEntity.ok().body("File uploaded");
        }
        else if (filtered.equals("false")) {
            try {
                movieService.createMovie(name);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            Movie movie = movieService.getByName(name);
            String path = null;
            try {
                path = storageService.saveUnfilteredVideo(file, movie.getMovieName());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            movie.setMoviePath(path);
            movieService.saveMovie(movie);

            if (doFilter.equals("true")) {
                //write some code to send to python service

                return ResponseEntity.ok().body("File uploaded and it is sent to the filtering service to det filtered!");
            }
            else return ResponseEntity.ok().body("File uploaded!");


        }

        return ResponseEntity.badRequest().body("Wrong Entry!");
        }


}

