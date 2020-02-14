package com.rafaelduarte.mvparquitechturetest.models;

public class GenresModel {

    private String name;
    private int movieID, tvShowID;

    public GenresModel(String name, int movieID, int tvShowID) {
        this.name = name;
        this.movieID = movieID;
        this.tvShowID = tvShowID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getTvShowID() {
        return tvShowID;
    }

    public void setTvShowID(int tvShowID) {
        this.tvShowID = tvShowID;
    }

}
