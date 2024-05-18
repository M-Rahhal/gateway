package repos;

import entity.FilteredMovie;
import entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilteredMovieRepository extends CrudRepository<FilteredMovie, Long> {
    Optional<FilteredMovie> findByMovieName(String movieName);

    List<FilteredMovie> findAll();
    Optional<FilteredMovie> findById(Long id);
    Object save(FilteredMovie movie);
}
