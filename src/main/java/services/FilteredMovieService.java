package services;


import entity.FilteredMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repos.FilteredMovieRepository;

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
