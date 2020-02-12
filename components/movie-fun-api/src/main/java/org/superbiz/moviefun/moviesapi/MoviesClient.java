package org.superbiz.moviefun.moviesapi;

import com.amazonaws.services.dynamodbv2.xspec.S;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class MoviesClient {

    private String moviesUrl;
    private RestOperations restOperations;

    public MoviesClient(String moviesUrl, RestOperations restOperations) {
        this.moviesUrl = moviesUrl;
        this.restOperations = restOperations;
    }

    public void addMovie(MovieInfo movie) {
        restOperations.postForObject(moviesUrl, movie, MovieInfo.class);
    }

    public void deleteMovieId(long id) {
        restOperations.delete(moviesUrl + "/" + id);
    }

    public List<MovieInfo> getMovies() {
        return restOperations.exchange(moviesUrl,
                        HttpMethod.GET,
                null,
                        new ParameterizedTypeReference<List<MovieInfo>>() {
                        }).getBody();
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("firstResult", firstResult)
                .queryParam("maxResults", maxResults);

        return restOperations.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovieInfo>>() {
                }).getBody();
    }

    public int countAll() {
        return restOperations.exchange(moviesUrl + "/counts",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Integer>() {
                }).getBody();
    }

    public int count(String field, String searchTerm) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl + "/counts")
                .queryParam("field", field)
                .queryParam("searchTerm", searchTerm);

        return restOperations.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Integer>() {
                }).getBody();
    }

    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("field", field)
                .queryParam("searchTerm", searchTerm)
                .queryParam("firstResult", firstResult)
                .queryParam("maxResults", maxResults);

        return restOperations.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovieInfo>>() {
                }).getBody();
    }
}
