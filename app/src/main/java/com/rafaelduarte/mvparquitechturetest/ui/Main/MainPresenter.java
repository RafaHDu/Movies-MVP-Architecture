package com.rafaelduarte.mvparquitechturetest.ui.Main;

import android.util.Log;
import android.widget.ImageView;

import com.rafaelduarte.mvparquitechturetest.models.MovieResponse;
import com.rafaelduarte.mvparquitechturetest.models.Result;
import com.rafaelduarte.mvparquitechturetest.network.NetworkClient;
import com.rafaelduarte.mvparquitechturetest.network.NetworkInterface;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainPresenterInterface{

    private MainViewInterface mvi;
    private String TAG = "MainPresenter";
    private int mCurrentPage = 1;

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }

    public Observable<MovieResponse> getObservable(){
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getMovies("fe31a23578bbe0ec3effe1caeb27f00d", "en-US", mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void onItemInteraction(Result result, ImageView imageView){
        mvi.openMovieDetailActivity(result, imageView);
    }

    @Override
    public void getMovies() {
        getObservable().subscribeWith(getObserver());
    }

    public void onLoadNextPage(){
        mCurrentPage++;
        getMovies();
    }

    public DisposableObserver<MovieResponse> getObserver(){
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
