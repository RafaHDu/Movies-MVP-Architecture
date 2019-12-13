package com.rafaelduarte.mvparquitechturetest.ui.MovieDetail;

import com.rafaelduarte.mvparquitechturetest.models.Result;

public class MovieDetailPresenter implements MovieDetailPresenterInterface {

    MovieDetailViewInterface mdvi;
    private Result result;

    public MovieDetailPresenter(MovieDetailViewInterface mdvi){
        this.mdvi = mdvi;
    }

    public void setResult(Result result){
        this.result = result;
    }

    public void setUpUIInfor(){
        mdvi.displayUI(result.getPosterPath(), result.getBackdropPath(), result.getOriginalTitle(), "2018", result.getOriginalLanguage(), "7", result.getOverview(), result.getId());
    }

}
