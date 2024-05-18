package com.streamPatform.gateway.repos;

import com.streamPatform.gateway.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<Movie> findByMovieName(String movieName);
    List<Movie> findAll();
    Optional<Movie> findById(Long id);
    Movie save(Movie movie);
}
