package services;

import entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repos.MovieRepository;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;
    public List<Movie> getAllMovies(){
        return repository.findAll();
    }
    public Movie getByName(String name){
        return repository.findByMovieName(name).get();
    }
    public Movie getById(Long id){
       return repository.findById(id).get();
    }

    public void saveMovie(Movie movie){
        repository.save(movie);
    }
    public void createMovie(String name){
        Movie movie = new Movie();
        movie.setMovieName(name);
        movie.setMoviePath("1");
        saveMovie(movie);
    }
}
