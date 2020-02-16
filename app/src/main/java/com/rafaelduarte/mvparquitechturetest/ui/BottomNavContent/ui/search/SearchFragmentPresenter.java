package com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.search;

import android.util.Log;

import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.MovieResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.TvShowResponse;
import com.rafaelduarte.mvparquitechturetest.network.TMDB.TMDBNetworkClient;
import com.rafaelduarte.mvparquitechturetest.network.TMDB.TMDBNetworkInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchFragmentPresenter {

    SearchFragmentInterface searchFragmentInterface;

    private String TAG = "SearchFragmentPresenter";

    public SearchFragmentPresenter(SearchFragmentInterface searchFragmentInterface){
        this.searchFragmentInterface = searchFragmentInterface;
    }




    //**************************************   Movies - TMDB   *************************************
    private Observable<MovieResponse> getObservableMovies(){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getTrendingMovies("fe31a23578bbe0ec3effe1caeb27f00d")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieResponse> getObserverMovies(){
        return new DisposableObserver<MovieResponse>() {

            @Override
            public void onNext(@NonNull MovieResponse movieResponse) {
                //Log.d(TAG,"OnNext"+ traktTvMoviesTrending.toString());
                searchFragmentInterface.displayMovies(movieResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TMDB_Peopke","Error"+e);
                e.printStackTrace();
                searchFragmentInterface.displayError("Error fetching Data - People");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void getMovies() {
        getObservableMovies().subscribeWith(getObserverMovies());
    }
    //**********************************************************************************************




    //**************************************   TvShow - TMDB   *************************************
    private Observable<TvShowResponse> getObservableTvShows(){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getTrendingTvShow("fe31a23578bbe0ec3effe1caeb27f00d")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<TvShowResponse> getObserverTvShows(){
        return new DisposableObserver<TvShowResponse>() {

            @Override
            public void onNext(@NonNull TvShowResponse tvShowResponse) {
                //Log.d(TAG,"OnNext"+ traktTvMoviesTrending.toString());
                searchFragmentInterface.displayTvShows(tvShowResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TMDB_Peopke","Error"+e);
                e.printStackTrace();
                searchFragmentInterface.displayError("Error fetching Data - People");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void getTvShows() {
        getObservableTvShows().subscribeWith(getObserverTvShows());
    }
    //**********************************************************************************************

}
