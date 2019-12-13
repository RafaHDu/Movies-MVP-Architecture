package com.rafaelduarte.mvparquitechturetest.ui.MovieDetail;

import java.util.ArrayList;

public interface MovieDetailViewInterface {

    void displayUI(final String pathPoster,
                   final String pathBG,
                   final String title,
                   final String year,
                   final String language,
                   final String rating,
                   final int id,
                   final String genres);

}
