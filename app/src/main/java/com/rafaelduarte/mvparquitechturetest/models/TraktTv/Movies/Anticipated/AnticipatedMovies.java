package com.rafaelduarte.mvparquitechturetest.models.TraktTv.Movies.Anticipated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rafaelduarte.mvparquitechturetest.models.TraktTv.Movies.Trending.Movie;

public class AnticipatedMovies {

    @SerializedName("list_count")
    @Expose
    private Integer listCount;
    @SerializedName("movie")
    @Expose
    private Movie movie;

    public Integer getListCount() {
        return listCount;
    }

    public void setListCount(Integer listCount) {
        this.listCount = listCount;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
