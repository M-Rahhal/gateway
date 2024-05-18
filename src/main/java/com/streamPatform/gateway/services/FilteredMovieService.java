package com.streamPatform.gateway.services;


import com.streamPatform.gateway.entity.FilteredMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.streamPatform.gateway.repos.FilteredMovieRepository;

@Service
public class FilteredMovieService {
    @Autowired
    private FilteredMovieRepository repository;

    public void createFilteredMovie(String name , String path){
        FilteredMovie movie = new FilteredMovie();
        movie.setMoviePath(path);
        movie.setMovieName(name);
        repository.save(movie);
    }
}
