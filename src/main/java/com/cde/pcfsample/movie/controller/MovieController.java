package com.cde.pcfsample.movie.controller;

import akka.http.javadsl.model.Multipart;
import com.cde.pcfsample.movie.model.Movie;
import com.cde.pcfsample.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RefreshScope
@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping(value = "/movie/{id}",produces =  "application/json")
    public List<Movie> getMovie(@PathVariable(name="id", required =  false) Integer id) {
        return movieService.getMovie(id);
    }


    @GetMapping(value = "/movie",produces =  "application/json")
    public List<Movie> getAllMovies(@PathVariable(name="id", required =  false) Integer id) {
        return movieService.getMovies();
    }


    @GetMapping(value="/config")
    public String getConfig() {
        return movieService.getRatingsUrl();
    }


    @PostMapping(value ="/movie", produces =  "application/json", consumes = "application/json")
    public Movie insertMovie(@RequestBody Movie movie) {
        return  movieService.insertMovie(movie);
    }
    @GetMapping(value="/foo")
    public String getFoo() {
        return  movieService.getFooValue();
    }


    @PostMapping(value="/movie/{id}/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file, @PathVariable String id) {
        try {
            String message = "";
            movieService.save(file, id);
            message = "Uploaded the file succeessfully" + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch(Exception e) {
            String message="Could not upload file" + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message + e.toString());

        }


    }
}
