package com.rafaelduarte.mvparquitechturetest.ui.MovieDetail;

public interface MovieDetailViewInterface {

    void displayUI(final String pathPoster,
                   final String pathBG,
                   final String title,
                   final String year,
                   final String language,
                   final String rating,
                   final String overview,
                   final int id);

}
