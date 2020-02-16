package com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.search;

import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.MovieResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.TvShowResponse;

public interface SearchFragmentInterface {

    //Movies
    void displayMovies(MovieResponse movieResponse);

    //TvShows
    void displayTvShows(TvShowResponse tvShowResponse);

    //Erro Genérico
    void displayError(String s);
}
