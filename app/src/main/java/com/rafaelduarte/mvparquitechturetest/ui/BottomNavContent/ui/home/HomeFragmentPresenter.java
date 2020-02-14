package com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.home;

import android.util.Log;
import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.rafaelduarte.mvparquitechturetest.models.CollectionsModel;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.ListsResponse.Item;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.ListsResponse.ListsResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieDetails.MovieDetailResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.People.People;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowDetails.TvShowDetailResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.TvShowResponse;
import com.rafaelduarte.mvparquitechturetest.network.TMDB.TMDBNetworkClient;
import com.rafaelduarte.mvparquitechturetest.network.TMDB.TMDBNetworkInterface;
import com.rafaelduarte.mvparquitechturetest.network.TraktTv.TraktTvNetworkClient;
import com.rafaelduarte.mvparquitechturetest.network.TraktTv.TraktTvNetworkInterface;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentPresenter {

    HomeFragmentInterface homeFragmentInterface;
    private String TAG = "HomeFragmentPresenter";
    private int netflixTvShowPage = 1, primeVideoPage = 1, peopleNextPage = 1;

    ArrayList<Integer> listCollections_Movies = new ArrayList<Integer>(Arrays.asList(112736, 112737, 112735, 112731, 112746, 112734, 112729, 112724, 112698, 112697));
    ArrayList<Integer> listCollections_TvShows = new ArrayList<Integer>(Arrays.asList(113392, 113391, 113399, 113398, 113401, 113389, 113403, 113393));
    private static final ArrayList<CollectionsModel> collectionList = new ArrayList<>();

    public HomeFragmentPresenter(HomeFragmentInterface homeFragmentInterface){
        this.homeFragmentInterface = homeFragmentInterface;
    }




    //*****************************   Networks Lists (Movies)   ***********************************
    private Observable<ListsResponse> getObservableList(String list){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getList(list, "fe31a23578bbe0ec3effe1caeb27f00d", "en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getList(String list, int network) {
        getObservableList(list).subscribeWith(getObserverList(network));
    }

    private DisposableObserver<ListsResponse> getObserverList(int network){
        return new DisposableObserver<ListsResponse>() {

            @Override
            public void onNext(@NonNull ListsResponse listsResponse) {
                Log.d(TAG,"OnNext"+ listsResponse.getItemCount());
                homeFragmentInterface.displayNetworksMovieList(listsResponse, network);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Networks_Lists","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayError("Error fetching Data - Networks Lists");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void onItemInteractionList(Item item, ImageView imageView){
        //homeFragmentInterface.openTvShowDetailActivity(item, imageView);
    }
    //**********************************************************************************************




    //*****************************   Discover by Networks (TvShows)   *****************************
    private Observable<TvShowResponse> getObservableDiscoverNetworks(int network){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getDiscoverNetworks("fe31a23578bbe0ec3effe1caeb27f00d",
                        "en-US",
                        "popularity.desc",
                        network, netflixTvShowPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getDiscoverNetworks(int networkID, int network) {
        getObservableDiscoverNetworks(networkID).subscribeWith(getObserverDiscoverNetworks(network));
    }

    public void onNetflixLoadNextPage(){
        ++netflixTvShowPage;
        getDiscoverNetworks(213, 0);
    }

    public void onPrimeVideoLoadNextPage(){
        ++primeVideoPage;
        getDiscoverNetworks(1024, 1);
    }

    private DisposableObserver<TvShowResponse> getObserverDiscoverNetworks(int network){
        return new DisposableObserver<TvShowResponse>() {

            @Override
            public void onNext(@NonNull TvShowResponse tvShowResponse) {
                Log.d(TAG,"OnNext"+ tvShowResponse.getTotalResults());
                homeFragmentInterface.displayNetworksTvShows(tvShowResponse, network);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Networks_Discover","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayError("Error fetching Data - Networks Discover");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }
    //**********************************************************************************************




    //******************** AnticipatedTvShow (Both Movies (0) and TvShows (1)) *********************
    private Observable<JsonArray> getObservableTraktTvAnticipated(int type, int page) {
        if(type == 0){
            //Movies
            return TraktTvNetworkClient.getRetrofit().create(TraktTvNetworkInterface.class)
                    .getMoviesAnticipated(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            //TvShow
            return TraktTvNetworkClient.getRetrofit().create(TraktTvNetworkInterface.class)
                    .getShowsAnticipated(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    private DisposableObserver<JsonArray> getObserverTraktTvAnticipated(int type){
        return new DisposableObserver<JsonArray>() {

            @Override
            public void onNext(@NonNull JsonArray jsonArray) {
                Log.d(TAG,"OnNextAnticipated"+ jsonArray.toString());
                homeFragmentInterface.displayAnticipatedResponse(jsonArray, type);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TraktTv_Anticipated","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayError("Error fetching Data - TraktTv AnticipatedTvShow");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void getTraktTvAnticipated(int type, int page) {
        getObservableTraktTvAnticipated(type, page).subscribeWith(getObserverTraktTvAnticipated(type));
    }

    //Movies
    private Observable<MovieDetailResponse> getObservableAnticipatedMovieDetail(String tmdbID){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getMovieDetails(String.valueOf(tmdbID),
                        "fe31a23578bbe0ec3effe1caeb27f00d",
                        "en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieDetailResponse> getObserverAnticipatedMovieDetail(){
        return new DisposableObserver<MovieDetailResponse>() {

            @Override
            public void onNext(@NonNull MovieDetailResponse movieDetailResponse) {
                Log.d(TAG,"OnNext"+ movieDetailResponse.toString());
                homeFragmentInterface.displayAnticipatedMovieDetail(movieDetailResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TMDB_MovieDetail_Antici","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayAnticipatedError("Error fetching Data - TMDB MovieDetail AnticipatedMovie", 0);
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    //TvShow
    private Observable<TvShowDetailResponse> getObservableAnticipatedTvShowDetail(String tmdbID){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getTvShowDetails(String.valueOf(tmdbID),
                        "fe31a23578bbe0ec3effe1caeb27f00d",
                        "en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<TvShowDetailResponse> getObserverAnticipatedTvShowDetail(){
        return new DisposableObserver<TvShowDetailResponse>() {

            @Override
            public void onNext(@NonNull TvShowDetailResponse tvShowDetailResponse) {
                Log.d(TAG,"OnNext"+ tvShowDetailResponse.toString());
                homeFragmentInterface.displayAnticipatedTvShowDetail(tvShowDetailResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TMDB_MovieDetail_Antici","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayAnticipatedError("Error fetching Data - TMDB MovieDetail AnticipatedMovie", 1);
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void getTraktTvAnticipatedMovie_TMDBDetail(int tmdb){
        getObservableAnticipatedMovieDetail(String.valueOf(tmdb)).subscribeWith(getObserverAnticipatedMovieDetail());
    }

    public void getTraktTvAnticipatedTvShow_TMDBDetail(int tmdb){
        getObservableAnticipatedTvShowDetail(String.valueOf(tmdb)).subscribeWith(getObserverAnticipatedTvShowDetail());
    }
    //**********************************************************************************************




    //************************************   Trending Movies   *************************************
    private Observable<JsonArray> getObservableTraktTvTrendingMovies(int page){
        return TraktTvNetworkClient.getRetrofit().create(TraktTvNetworkInterface.class)
                .getMoviesTrending(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<JsonArray> getObserverTraktTvTrendingMovies(){
        return new DisposableObserver<JsonArray>() {

            @Override
            public void onNext(@NonNull JsonArray jsonArray) {
                //Log.d(TAG,"OnNext"+ traktTvMoviesTrending.toString());
                homeFragmentInterface.displayTrendingMoviesResponse(jsonArray);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TraktTv_Trending_Movies","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayError("Error fetching Data - TraktTv TrendingMovies Movies");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void getTraktTvTrendingMovies(int page) {
        getObservableTraktTvTrendingMovies(page).subscribeWith(getObserverTraktTvTrendingMovies());
    }

    private Observable<MovieDetailResponse> getObservableTrendingMovieDetail(String tmdbID){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getMovieDetails(String.valueOf(tmdbID),
                        "fe31a23578bbe0ec3effe1caeb27f00d",
                        "en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieDetailResponse> getObserverTrendingMovieDetail(){
        return new DisposableObserver<MovieDetailResponse>() {

            @Override
            public void onNext(@NonNull MovieDetailResponse movieDetailResponse) {
                Log.d(TAG,"OnNext"+ movieDetailResponse.toString());
                homeFragmentInterface.displayTrendingMovieDetail(movieDetailResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Trending_Movie_Detail","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayTrendingMoviesError("Error fetching Data - TrendingMovies Movie Detail");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void getTraktTvTrendingMovie_TMDBDetail(int tmdb){
        getObservableTrendingMovieDetail(String.valueOf(tmdb)).subscribeWith(getObserverTrendingMovieDetail());
    }
    //**********************************************************************************************




    //************************************   Trending TvShows   ************************************
    private Observable<JsonArray> getObservableTraktTvTrendingTvShows(int page){
        return TraktTvNetworkClient.getRetrofit().create(TraktTvNetworkInterface.class)
                .getShowsTrending(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<JsonArray> getObserverTraktTvTrendingTvShows(){
        return new DisposableObserver<JsonArray>() {

            @Override
            public void onNext(@NonNull JsonArray jsonArray) {
                //Log.d(TAG,"OnNext"+ traktTvMoviesTrending.toString());
                homeFragmentInterface.displayTrendingTvShowsResponse(jsonArray);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TraktTv_Trending_Movies","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayError("Error fetching Data - TraktTv TrendingMovies Movies");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void getTraktTvTrendingTvShows(int page) {
        getObservableTraktTvTrendingTvShows(page).subscribeWith(getObserverTraktTvTrendingTvShows());
    }

    private Observable<TvShowDetailResponse> getObservableTrendingTvShowsDetail(String tmdbID){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getTvShowDetails(String.valueOf(tmdbID),
                        "fe31a23578bbe0ec3effe1caeb27f00d",
                        "en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<TvShowDetailResponse> getObserverTrendingTvShowsDetail(){
        return new DisposableObserver<TvShowDetailResponse>() {

            @Override
            public void onNext(@NonNull TvShowDetailResponse tvShowDetailResponse) {
                Log.d(TAG,"OnNext"+ tvShowDetailResponse.toString());
                homeFragmentInterface.displayTrendingTvShowsDetail(tvShowDetailResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Trending_Movie_Detail","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayTrendingTvShowsError("Error fetching Data - TrendingMovies Movie Detail");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void getTraktTvTrendingTvShow_TMDBDetail(int tmdb){
        getObservableTrendingTvShowsDetail(String.valueOf(tmdb)).subscribeWith(getObserverTrendingTvShowsDetail());
    }
    //**********************************************************************************************




    //***************************************   COLLECTIONS   **************************************
    public void generateCollection(int type){
        if (type == 0){
            for(int i = 0; i < listCollections_Movies.size(); i++){
                int tmdbCollection = listCollections_Movies.get(i);
                collectionList.add(generateMoviesCollection(tmdbCollection));
            }
            homeFragmentInterface.displayCollections(collectionList);
            collectionList.clear();
        } else {
            for(int i = 0; i < listCollections_TvShows.size(); i++){
                int tmdbCollection = listCollections_TvShows.get(i);
                collectionList.add(generateTvShowsCollection(tmdbCollection));
            }
            homeFragmentInterface.displayCollections(collectionList);
            collectionList.clear();
        }
    }

    private CollectionsModel generateMoviesCollection(int id){
        CollectionsModel collectionsModel = new CollectionsModel();
        switch (id){
            case 112736:
                collectionsModel = new CollectionsModel("DC Extended Universe", "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg", 112736);
                break;
            case 112737:
                collectionsModel = new CollectionsModel("Based on Marvel Comics", "/n1y094tVDFATSzkTnFxoGZ1qNsG.jpg", 112737);
                break;
            case 112735:
                collectionsModel = new CollectionsModel("Marvel Cinematic Universe", "/6ELJEzQJ3Y45HczvreC3dg0GV5R.jpg", 112735);
                break;
            case 112731:
                collectionsModel = new CollectionsModel("Heist Movies", "/1egC8NH3RLbebr3XPjdYVXGugpD.jpg", 112731);
                break;
            case 112746:
                collectionsModel = new CollectionsModel("Best Science Fiction Flicks", "/aHcth2AXzZSjhX7JYh7ie73YVNc.jpg", 112746);
                break;
            case 112734:
                collectionsModel = new CollectionsModel("Movies for Black Mirror Fans", "/qBmJKg3L2bHeIysbhdbwfVe2vlW.jpg", 112734);
                break;
            case 112729:
                collectionsModel = new CollectionsModel("Movies About Secret Agents", "/lcqVBBirzzX520AyOK3k7kXBDJ6.jpg", 112729);
                break;
            case 112724:
                collectionsModel = new CollectionsModel("Movies With Brilliant Plot Twists", "/mMZRKb3NVo5ZeSPEIaNW9buLWQ0.jpg", 112724);
                break;
            case 112698:
                collectionsModel = new CollectionsModel("The Essential Films Based on True Stories", "/eSbKT3v3RKFRGeOmxJbZhIvrevR.jpg", 112698);
                break;
            case 112697:
                collectionsModel = new CollectionsModel("Movies About The World War II", "/fudEG1VUWuOqleXv6NwCExK0VLy.jpg", 112697);
                break;
        }
        return collectionsModel;
    }

    private CollectionsModel generateTvShowsCollection(int id){
        CollectionsModel collectionsModel = new CollectionsModel();
        switch (id){
            case 113392:
                collectionsModel = new CollectionsModel("DC TV Series", "/d2YDPTQPe3mI2LqBWwb0CchN54f.jpg", 113392);
                break;
            case 113391:
                collectionsModel = new CollectionsModel("Marvel TV Series", "/7eV2vDrj1AwlTffUud66v9o0Ytq.jpg", 113391);
                break;
            case 113399:
                collectionsModel = new CollectionsModel("Creepy Series", "/ilKE2RPD8tkynAOHefX9ZclG1yq.jpg", 113399);
                break;
            case 113398:
                collectionsModel = new CollectionsModel("Syfy Series", "/mmPduTBlJQToB5mJoYIRDlJVCHP.jpg", 113398);
                break;
            case 113401:
                collectionsModel = new CollectionsModel("TV Dramas", "/900tHlUYUkp7Ol04XFSoAaEIXcT.jpg", 113401);
                break;
            case 113389:
                collectionsModel = new CollectionsModel("Popular TV Mini-Series", "/z2ZmYbhk0McewduBFtCqkmcZoqT.jpg", 113389);
                break;
            case 113403:
                collectionsModel = new CollectionsModel("TV Comedies", "/ArZwmJl1y6qlnLjuE9FXlEd8RU0.jpg", 113403);
                break;
            case 113393:
                collectionsModel = new CollectionsModel("Animated Series for Adults", "/ycnNgJwJoOfoE7PqQuouFALN6BZ.jpg", 113393);
                break;
        }
        return collectionsModel;
    }
    //**********************************************************************************************



    //******************************************   PEOPLE   ****************************************
    private Observable<People> getObservablePeople(int page){
        return TMDBNetworkClient.getRetrofit().create(TMDBNetworkInterface.class)
                .getPopularPeople("fe31a23578bbe0ec3effe1caeb27f00d", "en-US", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<People> getObserverPeople(){
        return new DisposableObserver<People>() {

            @Override
            public void onNext(@NonNull People people) {
                //Log.d(TAG,"OnNext"+ traktTvMoviesTrending.toString());
                homeFragmentInterface.displayPeople(people);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TMDB_Peopke","Error"+e);
                e.printStackTrace();
                homeFragmentInterface.displayError("Error fetching Data - People");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public void getPeople() {
        getObservablePeople(peopleNextPage).subscribeWith(getObserverPeople());
    }

    public void onPeopleLoadNextPage(){
        ++peopleNextPage;
        getPeople();
    }
    //**********************************************************************************************

}
