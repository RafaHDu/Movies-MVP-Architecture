package com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.home;

import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.rafaelduarte.mvparquitechturetest.models.CollectionsModel;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.ListsResponse.ListsResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieDetails.MovieDetailResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.People.People;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowDetails.TvShowDetailResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.ResultTvShow;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.TvShowResponse;

import java.util.List;

public interface HomeFragmentInterface {

    void showToast(String s);

    void openMovieDetailActivity(MovieDetailResponse result, ImageView imageView);
    void openTvShowDetailActivity(ResultTvShow result, ImageView imageView);
    void openList(ResultTvShow result, ImageView imageView);


    //Networks
    void displayNetworksMovieList(ListsResponse listsResponse, int network);
    void displayNetworksTvShows(TvShowResponse tvShowResponse, int network);


    //Anticipated
    void displayAnticipatedResponse(JsonArray jsonArray, int type);
    void displayAnticipatedError(String erro, int type);
    void displayAnticipatedMovieDetail(MovieDetailResponse movieDetailResponse);
    void displayAnticipatedTvShowDetail(TvShowDetailResponse tvShowDetailResponse);


    //TrendingMovies
    void displayTrendingMoviesResponse(JsonArray jsonArray);
    void displayTrendingMoviesError(String erro);
    void displayTrendingMovieDetail(MovieDetailResponse movieDetailResponse);


    //TrendingMovies
    void displayTrendingTvShowsResponse(JsonArray jsonArray);
    void displayTrendingTvShowsError(String erro);
    void displayTrendingTvShowsDetail(TvShowDetailResponse tvShowDetailResponse);


    //Collections
    void displayCollections(List<CollectionsModel> collectionsModelList);

    //
    void displayPeople(People people);


    //Erro Genérico
    void displayError(String s);
}
