package com.rafaelduarte.mvparquitechturetest.ui.Main;

import android.widget.ImageView;

import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.MovieResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.ResultMovies;

public interface MainViewInterface {

    void showToast(String s);
    void displayMovies(MovieResponse movieResponse);
    void displayError(String s);
    void openMovieDetailActivity(ResultMovies result, ImageView imageView);

}
