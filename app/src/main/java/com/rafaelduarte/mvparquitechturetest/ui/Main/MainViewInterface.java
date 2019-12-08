package com.rafaelduarte.mvparquitechturetest.ui.Main;

import com.rafaelduarte.mvparquitechturetest.models.MovieResponse;
import com.rafaelduarte.mvparquitechturetest.models.Result;

public interface MainViewInterface {

    void showToast(String s);
    void displayMovies(MovieResponse movieResponse);
    void displayError(String s);
    void displayResult(Result result);

}
