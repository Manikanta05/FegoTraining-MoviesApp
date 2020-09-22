package com.cde.pcfsample.movie.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cde.pcfsample.movie.model.Movie;
import com.cde.pcfsample.movie.model.Rating;
import com.cde.pcfsample.movie.repository.MvieRepository;
import com.google.inject.internal.asm.$TypePath;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class MovieService {

    private final Path root = Paths.get("uploads " );

    private static String configUrl = "http://RATING-SERVIE/ratings/1";

    public MovieService() {
        try {
            if (Files.exists(root)) {
                Files.delete(root);
            }
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    @Autowired
    RestTemplate restTemplate;

    @Value("${ratingsUrl:}")
    public String ratingsUrl;

    @Value("${info.description}")
    public String foo;

    @Autowired
    MvieRepository movieRepository;

    @Autowired
    AmazonS3 amazonS3;

//    @HystrixCommand(fallbackMethod =  "getFallBackMovies")
    public List<Movie> getMovies() {
        return (List<Movie>) movieRepository.findAll();

    }

    public Movie insertMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public String getFooValue() {
        return foo;
    }

    public List<Movie> getMovie(int id) {
        List<Movie> movieList = new ArrayList<>();
        Movie movie = movieRepository.findById(id).get();
        String url;
        if (ratingsUrl==null || ratingsUrl.isEmpty()) {
            System.out.println("Unable to fetch valid value from Config Server. ratingsUrl - " + ratingsUrl);
            url = configUrl;
        } else {
            url = ratingsUrl;
        }
        System.out.println("url" + url);
        ResponseEntity<Rating[]>  responseEntity= restTemplate.getForEntity(url, Rating[].class);
        Rating[] ratings = responseEntity.getBody();
        movie.setRatingList(Arrays.asList(ratings));
        movieList.add(movie);
        return movieList;
    }
    public String getRatingsUrl() {
        return ratingsUrl;
    }

    public List<Movie> getFallBackMovies() {
        System.out.println("FallBack Called !!!");
        List<Movie> movieList = new ArrayList<>();
        Movie movie = new Movie(1, "Dummy", "Dummy");
        movieList.add(movie);
        return movieList;
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            System.out.println("Error converting the multi-part file to file= " + ex.getMessage());
        }
        return file;
    }

    private void uploadFileToS3Bucket(final String bucketName, final File file) {
        final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
        System.out.println("Uploading file with name= " + uniqueFileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
        amazonS3.putObject(putObjectRequest);
    }

    public void save(MultipartFile file, String id) {
        try {
            System.out.println(this.root.resolve(file.getOriginalFilename().toString()));
            final File uploadFile = convertMultiPartFileToFile(file);
            uploadFileToS3Bucket("test", uploadFile);
//            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
