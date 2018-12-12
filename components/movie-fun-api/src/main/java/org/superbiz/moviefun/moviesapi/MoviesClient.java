package org.superbiz.moviefun.moviesapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class MoviesClient {

    private final RestOperations restOperations;
    private final String moviesUrl;

    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>()
    {};

    public MoviesClient(RestOperations restOperations, String moviesUrl) {
        this.restOperations = restOperations;
        this.moviesUrl = moviesUrl;
    }

    public void addMovie(MovieInfo movie) {
        restOperations.postForEntity(moviesUrl, movie, MovieInfo.class);
    }

    public void updateMovie(MovieInfo movie) {
        restOperations.postForEntity(moviesUrl, movie, MovieInfo.class);
    }

    public void deleteMovie(MovieInfo movie) {
    }

    public void deleteMovieId(long id) {
        restOperations.delete(moviesUrl + "/" + id);
    }

    public List<MovieInfo> getMovies() {
        return restOperations.exchange(moviesUrl, GET, null, movieListType).getBody();
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).getBody();
    }

    public int countAll() {
        return restOperations.getForObject(moviesUrl + "/count", Integer.class);
    }

    public int count(String field, String searchTerm) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl + "/count")
                .queryParam("field", field)
                .queryParam("key", searchTerm);

        return restOperations.getForObject(builder.toUriString(), Integer.class);
    }

    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("field", field)
                .queryParam("key", field)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).getBody();
    }

    public void clean() {

    }
}
