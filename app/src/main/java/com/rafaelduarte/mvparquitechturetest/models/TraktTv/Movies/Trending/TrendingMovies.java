package com.rafaelduarte.mvparquitechturetest.models.TraktTv.Movies.Trending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendingMovies {

    @SerializedName("watchers")
    @Expose
    private Integer watchers;
    @SerializedName("movie")
    @Expose
    private Movie movie;

    public Integer getWatchers() {
        return watchers;
    }

    public void setWatchers(Integer watchers) {
        this.watchers = watchers;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
