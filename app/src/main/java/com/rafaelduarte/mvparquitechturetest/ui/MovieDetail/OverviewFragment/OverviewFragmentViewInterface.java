package com.rafaelduarte.mvparquitechturetest.ui.MovieDetail.OverviewFragment;

public interface OverviewFragmentViewInterface {

    void displayOverviewInfo(String rating, String storyline);
    void displayError(String error);
    void showToast(String s);

}
