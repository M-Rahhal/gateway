package com.streamPatform.gateway.services;


import com.streamPatform.gateway.entity.FilteredMovie;
import com.streamPatform.gateway.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.streamPatform.gateway.repos.FilteredMovieRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FilteredMovieService {
    @Autowired
    private FilteredMovieRepository repository;

    @Value("${app.storage.filtered}")
    private String path;
    public List<FilteredMovie> getAllFiltered(){
        return repository.findAll();
    }
    public FilteredMovie getByName(String name){
        try{
            return repository.findByMovieName(name).get();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public void saveMovie(FilteredMovie movie){
        repository.save(movie);
    }

    public void createFilteredMovie(String name) throws Exception {
        FilteredMovie movie = null;
        try{
            movie = repository.findByMovieName(name).get();
            throw new Exception("The movie with the name:" + name+ " is already uploaded!");
        }
        catch (NoSuchElementException e){
            movie = new FilteredMovie();
            movie.setMoviePath(path+"/"+name);
            movie.setMovieName(name);
            repository.save(movie);
        }
    }

    public FilteredMovie getById(Long id){
        return repository.findById(id).get();
    }


}
