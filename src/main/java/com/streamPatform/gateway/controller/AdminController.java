package com.streamPatform.gateway.controller;

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

    @AdminAPI
    @PostMapping("/upload")
    public ResponseEntity uploadVideo(@RequestParam("video")MultipartFile file  , @RequestParam("video-name")String name) throws IOException {
        if (file.isEmpty())
            return ResponseEntity.badRequest().body("The file is Empty");

        byte[] bytes =  file.getBytes();
        String type = file.getContentType();
        if (!type.equals("video/mp4"))
            return ResponseEntity.badRequest().body("Unsupported format");
        movieService.createMovie(name);
        Movie movie = movieService.getByName(name);
        String path = storageService.uploadFile(Base64.getEncoder().encodeToString(bytes) , movie.getId().toString());
        movie.setMoviePath(path);
        movieService.saveMovie(movie);
        return ResponseEntity.ok().body("File uploaded");
    }

}

