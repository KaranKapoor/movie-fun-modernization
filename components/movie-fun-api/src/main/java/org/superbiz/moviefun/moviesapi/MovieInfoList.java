package org.superbiz.moviefun.moviesapi;

import java.util.ArrayList;
import java.util.List;

public class MovieInfoList {

    private List<MovieInfo> movies;

    public MovieInfoList() {
        movies = new ArrayList<>();
    }

    public List<MovieInfo> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieInfo> movies) {
        this.movies = movies;
    }
}