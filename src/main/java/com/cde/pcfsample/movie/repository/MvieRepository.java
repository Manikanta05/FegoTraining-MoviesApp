package com.cde.pcfsample.movie.repository;

import com.cde.pcfsample.movie.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MvieRepository extends CrudRepository<Movie, Integer> {
}
