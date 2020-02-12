package org.superbiz.moviefun.movies;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @PostMapping
    public ResponseEntity addMovie(@RequestBody Movie movie) {
        moviesRepository.addMovie(movie);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteMovieId(@PathVariable long id) {
        moviesRepository.deleteMovieId(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String firstResult,
            @RequestParam(required = false) String maxResults
            ) {

        if (field != null && searchTerm != null) {
            return new ResponseEntity<>(moviesRepository.findRange(field, searchTerm, Integer.valueOf(firstResult), Integer.valueOf(maxResults)), HttpStatus.OK);
        } else if (firstResult != null && maxResults != null) {
            return new ResponseEntity<>(moviesRepository.findAll(Integer.valueOf(firstResult), Integer.valueOf(maxResults)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(moviesRepository.getMovies(), HttpStatus.OK);
        }
    }

    @GetMapping("/counts")
    public ResponseEntity<Integer> count(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String searchTerm
    ) {
        if (field != null && searchTerm != null) {
            return new ResponseEntity<>(moviesRepository.count(field, searchTerm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(moviesRepository.countAll(), HttpStatus.OK);
        }
    }
}
