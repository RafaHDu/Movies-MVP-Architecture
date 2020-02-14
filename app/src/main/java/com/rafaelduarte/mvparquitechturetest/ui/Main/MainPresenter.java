package com.rafaelduarte.mvparquitechturetest.ui.Main;

import android.util.Log;
import android.widget.ImageView;

import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.MovieResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.ResultMovies;
import com.rafaelduarte.mvparquitechturetest.network.TMDB.TMDBNetworkClient;
import com.rafaelduarte.mvparquitechturetest.network.TMDB.TMDBNetworkInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {

    private MainViewInterface mvi;
    private String TAG = "MainPresenter";
    private int mCurrentPage = 1;

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }

    private Observable<MovieResponse> getObservable(){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getMovies("fe31a23578bbe0ec3effe1caeb27f00d", "en-US", mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void onItemInteraction(ResultMovies result, ImageView imageView){
        mvi.openMovieDetailActivity(result, imageView);
    }

    public void getMovies() {
        getObservable().subscribeWith(getObserver());
    }

    public void onLoadNextPage(){
        mCurrentPage++;
        getMovies();
    }

    private DisposableObserver<MovieResponse> getObserver(){
        return new DisposableObserver<MovieResponse>() {

            @Override
            public void onNext(@NonNull MovieResponse movieResponse) {
                Log.d(TAG,"OnNext"+movieResponse.getTotalResults());
                mvi.displayMovies(movieResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                mvi.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

}
