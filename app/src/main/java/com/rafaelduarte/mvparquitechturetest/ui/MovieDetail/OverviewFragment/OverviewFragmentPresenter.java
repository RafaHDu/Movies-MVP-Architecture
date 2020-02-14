package com.rafaelduarte.mvparquitechturetest.ui.MovieDetail.OverviewFragment;

import android.util.Log;

import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieDetails.MovieDetailResponse;
import com.rafaelduarte.mvparquitechturetest.network.TMDB.TMDBNetworkClient;
import com.rafaelduarte.mvparquitechturetest.network.TMDB.TMDBNetworkInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class OverviewFragmentPresenter {

    OverviewFragmentViewInterface ofvi;
    String tmdbID;
    private String TAG = "OverviewFragmentPresenter";

    public OverviewFragmentPresenter(OverviewFragmentViewInterface ofvi, int tmdbID){
        this.ofvi = ofvi;
        this.tmdbID = String.valueOf(tmdbID);
    }

    private Observable<MovieDetailResponse> getObservable(){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getMovieDetails(tmdbID, "fe31a23578bbe0ec3effe1caeb27f00d", "en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getMovieDetails() {
        getObservable().subscribeWith(getObserver());
    }

    private DisposableObserver<MovieDetailResponse> getObserver(){
        return new DisposableObserver<MovieDetailResponse>() {

            @Override
            public void onNext(@NonNull MovieDetailResponse movieResponse) {
                Log.d(TAG,"OnNext");
                ofvi.displayOverviewInfo(String.valueOf(movieResponse.getVoteAverage()),
                        movieResponse.getOverview());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                ofvi.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

}
