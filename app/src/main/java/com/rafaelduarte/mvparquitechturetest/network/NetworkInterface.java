package com.rafaelduarte.mvparquitechturetest.network;

import io.reactivex.Observable;

import com.rafaelduarte.mvparquitechturetest.models.MovieResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {

    //Interface for all API calls

    //https://api.themoviedb.org/3/discover/movie?api_key=fe31a23578bbe0ec3effe1caeb27f00d&language=fr-FR
    @GET("discover/movie")
    Observable<MovieResponse> getMovies(@Query("api_key") String api_key, @Query("language") String language_code, @Query("page") int page);

}
