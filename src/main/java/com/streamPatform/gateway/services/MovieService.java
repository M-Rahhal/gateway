package com.streamPatform.gateway.services;

import com.streamPatform.gateway.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.streamPatform.gateway.repos.MovieRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

    @Value("${app.storage.unfiltered}")
    private String path;
    public List<Movie> getAllMovies(){
        return repository.findAll();
    }
    public Movie getByName(String name){
        try{
            return repository.findByMovieName(name).get();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
        }
    public Movie getById(Long id){
       return repository.findById(id).get();
    }

    public void saveMovie(Movie movie){
        repository.save(movie);
    }
    public void createMovie(String name) throws Exception {
        Movie movie = null;
        try{
            movie = repository.findByMovieName(name).get();
            throw new Exception("The movie with the name:" + name+ " is already uploaded!");
        }
        catch (NoSuchElementException e){
            movie = new Movie();
            movie.setMoviePath(path+"/"+name);
            movie.setMovieName(name);
            repository.save(movie);
        }
    }
}
