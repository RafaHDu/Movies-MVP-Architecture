package com.rafaelduarte.mvparquitechturetest.network.TMDB;

import io.reactivex.Observable;

import com.rafaelduarte.mvparquitechturetest.models.TMDB.ListsResponse.ListsResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieDetails.MovieDetailResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.MovieResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.People.People;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowDetails.TvShowDetailResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.TvShowResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBNetworkInterface {

    //Interface for all API calls

    //https://api.themoviedb.org/3/discover/movie?api_key=fe31a23578bbe0ec3effe1caeb27f00d&language=en-US
    @GET("discover/movie")
    Observable<MovieResponse> getMovies(@Query("api_key") String api_key, @Query("language") String language_code, @Query("page") int page);


    //https://api.themoviedb.org/3/movie/63639?api_key=fe31a23578bbe0ec3effe1caeb27f00d&language=en-US
    @GET("movie/{id}")
    Observable<MovieDetailResponse> getMovieDetails(@Path("id") String id, @Query("api_key") String api_key, @Query("language") String language_code);


    //https://api.themoviedb.org/3/trending/movie/day?api_key=fe31a23578bbe0ec3effe1caeb27f00d
    @GET("trending/movie/day")
    Observable<MovieResponse> getTrendingMovies(@Query("api_key") String api_key);


    //https://api.themoviedb.org/3/trending/tv/day?api_key=fe31a23578bbe0ec3effe1caeb27f00d
    @GET("trending/tv/day")
    Observable<TvShowResponse> getTrendingTvShow(@Query("api_key") String api_key);


    //https://api.themoviedb.org/3/list/43893?api_key=fe31a23578bbe0ec3effe1caeb27f00d&language=en-US
    @GET("list/{listID}")
    Observable<ListsResponse> getList(@Path("listID") String listID, @Query("api_key") String api_key, @Query("language") String language_code);


    //Discover -> By Networks
    //https://api.themoviedb.org/3/discover/tv?api_key=fe31a23578bbe0ec3effe1caeb27f00d&language=en-US&sort_by=popularity.desc&with_networks=213&page=1
    @GET("discover/tv")
    Observable<TvShowResponse> getDiscoverNetworks(@Query("api_key") String api_key,
                                                  @Query("language") String language_code,
                                                  @Query("sort_by") String sort_by,
                                                  @Query("with_networks") int network,
                                                  @Query("page") int page);


    //https://api.themoviedb.org/3/tv/1396?api_key=fe31a23578bbe0ec3effe1caeb27f00d&language=en-US
    @GET("tv/{id}")
    Observable<TvShowDetailResponse> getTvShowDetails(@Path("id") String id, @Query("api_key") String api_key, @Query("language") String language_code);


    //https://api.themoviedb.org/3/person/popular?api_key=fe31a23578bbe0ec3effe1caeb27f00d&language=en-US&page=1
    @GET("person/popular")
    Observable<People> getPopularPeople(@Query("api_key") String api_key, @Query("language") String language_code, @Query("page") int page);
}
