package com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.adapters.Home.CollectionsAdapter;
import com.rafaelduarte.mvparquitechturetest.adapters.Home.PeopleAdapter;
import com.rafaelduarte.mvparquitechturetest.adapters.Home.TraktTvAnticipatedAdapter;
import com.rafaelduarte.mvparquitechturetest.adapters.Home.TraktTvTrendingMoviesAdapter;
import com.rafaelduarte.mvparquitechturetest.adapters.Home.TraktTvTrendingTvShowsAdapter;
import com.rafaelduarte.mvparquitechturetest.adapters.ItemDecorators.ItemDecorator;
import com.rafaelduarte.mvparquitechturetest.adapters.Home.NetflixAdapter;
import com.rafaelduarte.mvparquitechturetest.adapters.Home.PrimeVideoAdapter;
import com.rafaelduarte.mvparquitechturetest.models.CollectionsModel;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.ListsResponse.ListsResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieDetails.MovieDetailResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.People.People;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowDetails.TvShowDetailResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.ResultTvShow;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.TvShowResponse;
import com.rafaelduarte.mvparquitechturetest.models.TraktTv.Movies.Anticipated.AnticipatedMovies;
import com.rafaelduarte.mvparquitechturetest.models.TraktTv.Movies.Trending.TrendingMovies;
import com.rafaelduarte.mvparquitechturetest.models.TraktTv.TvShows.Anticipated.AnticipatedTvShow;
import com.rafaelduarte.mvparquitechturetest.models.TraktTv.TvShows.Trending.TrendingTvShow;
import com.rafaelduarte.mvparquitechturetest.ui.MovieDetail.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements HomeFragmentInterface {

    @BindView(R.id.rvAnticipated)
    RecyclerView rvAnticipated;

    @BindView(R.id.rvTrendingMovies)
    RecyclerView rvTrendingMovies;

    @BindView(R.id.rvTrendingTvShows)
    RecyclerView rvTrendingTvShows;

    @BindView(R.id.rvNetFlix)
    RecyclerView rvNetflix;
    @BindView(R.id.tabsNetflix)
    TabLayout tabsNetflix;

    @BindView(R.id.rvPrimeVideo)
    RecyclerView rvPrimeVideo;
    @BindView(R.id.tabsPrimeVideo)
    TabLayout tabsPrimeVideo;

    @BindView(R.id.img_collection_movie)
    ImageView img_collection_movie;
    @BindView(R.id.img_collection_tvshow)
    ImageView img_collection_tvshow;

    @BindView(R.id.rvCollections)
    RecyclerView rvCollections;
    @BindView(R.id.tvCollectionsLabel)
    TextView tvCollectionsLabel;
    @BindView(R.id.ivCollectionsMovie)
    ImageView ivCollectionsMovie;
    @BindView(R.id.ivCollectionsTvShow)
    ImageView ivCollectionsTvShow;

    @BindView(R.id.rvPeople)
    RecyclerView rvPeople;

    HomeFragmentPresenter homeFragmentPresenter;

    //Adapters
    TraktTvAnticipatedAdapter traktTvAnticipatedAdapter;
    TraktTvTrendingMoviesAdapter traktTvTrendingMoviesAdapter;
    TraktTvTrendingTvShowsAdapter traktTvTrendingTvShowsAdapter;
    NetflixAdapter netflixAdapter;
    PrimeVideoAdapter primeVideoAdapter;
    CollectionsAdapter collectionsAdapter;
    PeopleAdapter peopleAdapter;

    LinearLayoutManager layoutManager, layoutManagerTrendingMovies, layoutManagerTrendingTvShows, layoutManagerNetflix, layoutManagerPrimeVideo, layoutManagerCollections,
                    layoutManagerPeople;

    public static final String EXTRA_RESULT = "movieDetailResult";

    boolean isScrollingAnticipated = false, isScrollingTrendingMovies = false, isScrollingTrendingTvShows = false, isScrollingNetflix = false, isScrollingPrimeVideo = false, isScrollingPeople = false,
            anticipatedSwitch = false, collectionSwitch = false;

    ArrayList<AnticipatedMovies> traktTvMoviesAnticipated = new ArrayList<>();
    ArrayList<MovieDetailResponse> moviesAnticipated = new ArrayList<>();

    ArrayList<AnticipatedTvShow> tratktTvShowAnticipated = new ArrayList<>();
    ArrayList<TvShowDetailResponse> tvShowAnticipated = new ArrayList<>();

    ArrayList<TrendingMovies> traktTvMoviesTrending = new ArrayList<>();
    ArrayList<MovieDetailResponse> moviesTrending = new ArrayList<>();

    ArrayList<TrendingTvShow> traktTvTvShowsTrending = new ArrayList<>();
    ArrayList<TvShowDetailResponse> tvShowsTrending = new ArrayList<>();

    ArrayList<People> people = new ArrayList<>();

    private int moviesTrendingSize = 0, countTrendingMovies = 0,
                tvShowsTrendingSize = 0, countTrendingTvShows = 0,
            moviesAnticipatedSize = 0, tvShowAnticipatedSize = 0, countAnticipated = 0;

    private int anticipatedPage = 1, trendingMoviesPage = 1, trendingTvShowsPage = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);

        setupMVP();
        setupViews();
        getTraktTvAnticipated(0);
        getTraktTvTrendingMovies();
        getTraktTvTrendingTvShows();
        getNetflixMoviesList();
        getPrimeVideoMoviesList();
        getCollection(0);
        getPeople();

        rvAnticipated.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //fica True quando é dado Scroll de modo a poder chamar o Data Fetch e se assim for é retomado a False
                    isScrollingAnticipated = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                //if (isScrollingAnticipated && ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 4 && firstVisibleItemPosition >= 0 && totalItemCount >= 10)) {
                if (isScrollingAnticipated && (visibleItemCount + firstVisibleItemPosition == totalItemCount -6)) {
                    isScrollingAnticipated = false;
                    anticipatedPage+=1;
                    //anticipatedSwitch    -->    false = Movies   |   true = TvShows
                    if (anticipatedSwitch){
                        homeFragmentPresenter.getTraktTvAnticipated(1, anticipatedPage);
                    } else {
                        homeFragmentPresenter.getTraktTvAnticipated(0, anticipatedPage);
                    }
                }
            }
        });

        img_collection_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //anticipatedSwitch    -->    false = Movies   |   true = TvShows
                if (anticipatedSwitch){
                    anticipatedSwitch = false;
                    anticipatedPage = 1;

                    img_collection_movie.setImageAlpha(255);
                    img_collection_tvshow.setImageAlpha(40);

                    rvAnticipated.removeAllViewsInLayout();
                    tvShowAnticipated.clear();
                    traktTvAnticipatedAdapter = new TraktTvAnticipatedAdapter(0, new ArrayList<>(), new ArrayList<>(), getContext(), homeFragmentPresenter);
                    rvAnticipated.setAdapter(traktTvAnticipatedAdapter);
                    getTraktTvAnticipated(0);
                }
            }
        });

        img_collection_tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //anticipatedSwitch    -->    false = Movies   |   true = TvShows
                if (!anticipatedSwitch){
                    anticipatedSwitch = true;
                    anticipatedPage = 1;

                    img_collection_movie.setImageAlpha(40);
                    img_collection_tvshow.setImageAlpha(255);

                    rvAnticipated.removeAllViewsInLayout();
                    moviesAnticipated.clear();
                    traktTvAnticipatedAdapter = new TraktTvAnticipatedAdapter(1, new ArrayList<>(), new ArrayList<>(), getContext(), homeFragmentPresenter);
                    rvAnticipated.setAdapter(traktTvAnticipatedAdapter);
                    getTraktTvAnticipated(1);
                }
            }
        });

        rvTrendingMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //fica True quando é dado Scroll de modo a poder chamar o Data Fetch e se assim for é retomado a False
                    isScrollingTrendingMovies = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManagerTrendingMovies.getChildCount();
                int totalItemCount = layoutManagerTrendingMovies.getItemCount();
                int firstVisibleItemPosition = layoutManagerTrendingMovies.findFirstVisibleItemPosition();

                //if (isScrollingTrendingMovies && ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 4 && firstVisibleItemPosition >= 0 && totalItemCount >= 10)) {
                if (isScrollingTrendingMovies && (visibleItemCount + firstVisibleItemPosition == totalItemCount -6)) {
                    isScrollingTrendingMovies = false;
                    trendingMoviesPage+=1;
                    homeFragmentPresenter.getTraktTvTrendingMovies(trendingMoviesPage);
                }
            }
        });

        rvTrendingTvShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //fica True quando é dado Scroll de modo a poder chamar o Data Fetch e se assim for é retomado a False
                    isScrollingTrendingTvShows = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManagerTrendingTvShows.getChildCount();
                int totalItemCount = layoutManagerTrendingTvShows.getItemCount();
                int firstVisibleItemPosition = layoutManagerTrendingTvShows.findFirstVisibleItemPosition();

                if (isScrollingTrendingTvShows && (visibleItemCount + firstVisibleItemPosition == totalItemCount -6)) {
                    isScrollingTrendingTvShows = false;
                    trendingTvShowsPage+=1;
                    homeFragmentPresenter.getTraktTvTrendingTvShows(trendingTvShowsPage);
                }
            }
        });

        rvNetflix.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //fica True quando é dado Scroll de modo a poder chamar o Data Fetch e se assim for é retomado a False
                    isScrollingNetflix = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (isScrollingNetflix && ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 4 && firstVisibleItemPosition >= 0 && totalItemCount >= 10)) {
                    //if (isScrollingAnticipated && (visibleItemCount + firstVisibleItemPosition == totalItemCount)) {
                    homeFragmentPresenter.onNetflixLoadNextPage();
                }
            }
        });

        tabsNetflix.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        rvNetflix.removeAllViewsInLayout();
                        moviesTrending.clear();
                        netflixAdapter = new NetflixAdapter(0, new ArrayList<>(), new ArrayList<>(), getContext(), homeFragmentPresenter);
                        rvNetflix.setAdapter(netflixAdapter);
                        getNetflixMoviesList();
                        break;
                    case 1:
                        rvNetflix.removeAllViewsInLayout();
                        moviesTrending.clear();
                        netflixAdapter = new NetflixAdapter(1, new ArrayList<>(), new ArrayList<>(), getContext(), homeFragmentPresenter);
                        rvNetflix.setAdapter(netflixAdapter);
                        getNetflixTvShowList();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        rvPrimeVideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //fica True quando é dado Scroll de modo a poder chamar o Data Fetch e se assim for é retomado a False
                    isScrollingPrimeVideo = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (isScrollingPrimeVideo && ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 4 && firstVisibleItemPosition >= 0 && totalItemCount >= 10)) {
                    //if (isScrollingAnticipated && (visibleItemCount + firstVisibleItemPosition == totalItemCount)) {
                    homeFragmentPresenter.onPrimeVideoLoadNextPage();
                }
            }
        });

        tabsPrimeVideo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        rvPrimeVideo.removeAllViewsInLayout();
                        primeVideoAdapter = new PrimeVideoAdapter(0, new ArrayList<>(), new ArrayList<>(), getContext(), homeFragmentPresenter);
                        rvPrimeVideo.setAdapter(primeVideoAdapter);
                        getPrimeVideoMoviesList();
                        break;
                    case 1:
                        rvPrimeVideo.removeAllViewsInLayout();
                        primeVideoAdapter = new PrimeVideoAdapter(1, new ArrayList<>(), new ArrayList<>(), getContext(), homeFragmentPresenter);
                        rvPrimeVideo.setAdapter(primeVideoAdapter);
                        getPrimeVideoTvShowList();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ivCollectionsMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionSwitch){
                    collectionSwitch = false;

                    ivCollectionsMovie.setImageAlpha(255);
                    ivCollectionsTvShow.setImageAlpha(40);

                    rvCollections.removeAllViewsInLayout();
                    collectionsAdapter = new CollectionsAdapter(new ArrayList<>(), getContext(), homeFragmentPresenter);
                    rvCollections.setAdapter(collectionsAdapter);
                    getCollection(0);
                    tvCollectionsLabel.setText("MOVIES");
                }
            }
        });

        ivCollectionsTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!collectionSwitch){
                    collectionSwitch = true;

                    ivCollectionsMovie.setImageAlpha(40);
                    ivCollectionsTvShow.setImageAlpha(255);

                    rvCollections.removeAllViewsInLayout();
                    collectionsAdapter = new CollectionsAdapter(new ArrayList<>(), getContext(), homeFragmentPresenter);
                    rvCollections.setAdapter(collectionsAdapter);
                    getCollection(1);
                    tvCollectionsLabel.setText("TV SHOWS");
                }
            }
        });

        rvPeople.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //fica True quando é dado Scroll de modo a poder chamar o Data Fetch e se assim for é retomado a False
                    isScrollingPeople = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (isScrollingPeople && ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 4 && firstVisibleItemPosition >= 0 && totalItemCount >= 10)) {
                    //if (isScrollingAnticipated && (visibleItemCount + firstVisibleItemPosition == totalItemCount)) {
                    homeFragmentPresenter.onPeopleLoadNextPage();
                }
            }
        });

        return root;
    }

    private void setupMVP() {
        homeFragmentPresenter = new HomeFragmentPresenter(this);
    }

    public void setupViews() {
        img_collection_movie.setImageAlpha(255);
        img_collection_tvshow.setImageAlpha(40);

        ivCollectionsMovie.setImageAlpha(255);
        ivCollectionsTvShow.setImageAlpha(40);

        traktTvAnticipatedAdapter = new TraktTvAnticipatedAdapter(0, new ArrayList<>(), new ArrayList<>(), getContext(), homeFragmentPresenter);
        rvAnticipated.setAdapter(traktTvAnticipatedAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvAnticipated.setLayoutManager(layoutManager);
        rvAnticipated.setHasFixedSize(true);
        rvAnticipated.setItemAnimator(null);
        // TOP - BOTTOM - LEFT - RIGHT
        rvAnticipated.addItemDecoration(new ItemDecorator(0, 0, 32, 32));

        traktTvTrendingMoviesAdapter = new TraktTvTrendingMoviesAdapter(new ArrayList<>(), getContext(), homeFragmentPresenter);
        rvTrendingMovies.setAdapter(traktTvTrendingMoviesAdapter);
        layoutManagerTrendingMovies = new LinearLayoutManager(getContext());
        layoutManagerTrendingMovies.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTrendingMovies.setLayoutManager(layoutManagerTrendingMovies);
        rvTrendingMovies.setHasFixedSize(true);
        rvTrendingMovies.setItemAnimator(null);
        // TOP - BOTTOM - LEFT - RIGHT
        rvTrendingMovies.addItemDecoration(new ItemDecorator(0, 0, 32, 32));

        traktTvTrendingTvShowsAdapter = new TraktTvTrendingTvShowsAdapter(new ArrayList<>(), getContext(), homeFragmentPresenter);
        rvTrendingTvShows.setAdapter(traktTvTrendingTvShowsAdapter);
        layoutManagerTrendingTvShows = new LinearLayoutManager(getContext());
        layoutManagerTrendingTvShows.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTrendingTvShows.setLayoutManager(layoutManagerTrendingTvShows);
        rvTrendingTvShows.setHasFixedSize(true);
        rvTrendingTvShows.setItemAnimator(null);
        // TOP - BOTTOM - LEFT - RIGHT
        rvTrendingTvShows.addItemDecoration(new ItemDecorator(0, 0, 32, 32));

        netflixAdapter = new NetflixAdapter(0, new ArrayList<>(), new ArrayList<>(), getContext(), homeFragmentPresenter);
        rvNetflix.setAdapter(netflixAdapter);
        layoutManagerNetflix = new LinearLayoutManager(getContext());
        layoutManagerNetflix.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvNetflix.setLayoutManager(layoutManagerNetflix);
        rvNetflix.setHasFixedSize(true);
        rvNetflix.setItemAnimator(null);
        // TOP - BOTTOM - LEFT - RIGHT
        rvNetflix.addItemDecoration(new ItemDecorator(0, 0, 32, 32));

        primeVideoAdapter = new PrimeVideoAdapter(0, new ArrayList<>(), new ArrayList<>(), getContext(), homeFragmentPresenter);
        rvPrimeVideo.setAdapter(primeVideoAdapter);
        layoutManagerPrimeVideo = new LinearLayoutManager(getContext());
        layoutManagerPrimeVideo.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPrimeVideo.setLayoutManager(layoutManagerPrimeVideo);
        rvPrimeVideo.setHasFixedSize(true);
        rvPrimeVideo.setItemAnimator(null);
        // TOP - BOTTOM - LEFT - RIGHT
        rvPrimeVideo.addItemDecoration(new ItemDecorator(0, 0, 32, 32));

        collectionsAdapter = new CollectionsAdapter(new ArrayList<>(), getContext(), homeFragmentPresenter);
        rvCollections.setAdapter(collectionsAdapter);
        layoutManagerCollections = new LinearLayoutManager(getContext());
        layoutManagerCollections.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCollections.setLayoutManager(layoutManagerCollections);
        rvCollections.setHasFixedSize(true);
        rvCollections.setItemAnimator(null);
        // TOP - BOTTOM - LEFT - RIGHT
        rvCollections.addItemDecoration(new ItemDecorator(0, 0, 32, 32));
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvCollections);

        peopleAdapter = new PeopleAdapter(new ArrayList<>(), getContext(), homeFragmentPresenter);
        rvPeople.setAdapter(peopleAdapter);
        layoutManagerPeople = new LinearLayoutManager(getContext());
        layoutManagerPeople.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPeople.setLayoutManager(layoutManagerPeople);
        rvPeople.setHasFixedSize(true);
        rvPeople.setItemAnimator(null);
        // TOP - BOTTOM - LEFT - RIGHT
        rvPeople.addItemDecoration(new ItemDecorator(0, 0, 32, 32));
    }

    private void getTraktTvAnticipated(int type) {
        homeFragmentPresenter.getTraktTvAnticipated(type, anticipatedPage);
    }

    private void getTraktTvTrendingMovies() { homeFragmentPresenter.getTraktTvTrendingMovies(trendingMoviesPage); }

    private void getTraktTvTrendingTvShows() { homeFragmentPresenter.getTraktTvTrendingTvShows(trendingTvShowsPage); }

    private void getNetflixMoviesList() {
        homeFragmentPresenter.getList("43893", 0);
    }

    private void getNetflixTvShowList() {
        homeFragmentPresenter.getDiscoverNetworks(213, 0);
    }

    private void getPrimeVideoMoviesList() {
        homeFragmentPresenter.getList("112522", 1);
    }

    private void getPrimeVideoTvShowList() {
        homeFragmentPresenter.getDiscoverNetworks(1024, 1);
    }

    private void getCollection(int type){homeFragmentPresenter.generateCollection(type);}

    private void getPeople(){homeFragmentPresenter.getPeople();}




    @Override
    public void openMovieDetailActivity(MovieDetailResponse result, ImageView imageView) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);

        //TODO: PARCELABLE!!! ResultMovies tem parcelable... fazer o mesmo no MovieDetail
        //intent.putExtra(EXTRA_RESULT, result);

        //create the animation
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageView, "sharedName");
        startActivity(intent, options.toBundle());
        //startActivity(intent);
    }

    @Override
    public void openTvShowDetailActivity(ResultTvShow result, ImageView imageView) { }

    @Override
    public void openList(ResultTvShow result, ImageView imageView) { }




    //**************************************   ANTICIPATED   **************************************
    @Override
    public void displayAnticipatedResponse(JsonArray jsonArray, int type) {
        Gson gson = new Gson();
        if (type == 0) {
            moviesAnticipatedSize = jsonArray.size();
            //traktTvMoviesTrending = gson.fromJson(jsonArray.toString(), ArrayList.class);
            traktTvMoviesAnticipated = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<AnticipatedMovies>>() {}.getType());
            if (traktTvMoviesAnticipated.get(0).getMovie().getIds().getTmdb() != null) {
                homeFragmentPresenter.getTraktTvAnticipatedMovie_TMDBDetail(traktTvMoviesAnticipated.get(0).getMovie().getIds().getTmdb());
            }
        } else {
            tvShowAnticipatedSize = jsonArray.size();
            tratktTvShowAnticipated = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<AnticipatedTvShow>>() {}.getType());
            if (tratktTvShowAnticipated.get(countAnticipated).getShow().getIds().getTmdb() != null) {
                homeFragmentPresenter.getTraktTvAnticipatedTvShow_TMDBDetail(tratktTvShowAnticipated.get(0).getShow().getIds().getTmdb());
            }
        }
    }

    @Override
    public void displayAnticipatedError(String erro, int type) {
        if (type == 0){
            //Movie
            countAnticipated++;
            if (countAnticipated == moviesAnticipatedSize) {
                traktTvAnticipatedAdapter.addItems(moviesAnticipated, null);
                moviesAnticipated.clear();
                countAnticipated = 0;
            } else {
                homeFragmentPresenter.getTraktTvAnticipatedMovie_TMDBDetail(traktTvMoviesAnticipated.get(countAnticipated).getMovie().getIds().getTmdb());
            }
        } else {
            //TvShow
            countAnticipated++;
            if (countAnticipated == tvShowAnticipatedSize) {
                traktTvAnticipatedAdapter.addItems(null, tvShowAnticipated);
                tvShowAnticipated.clear();
                countAnticipated = 0;
            } else {
                homeFragmentPresenter.getTraktTvAnticipatedTvShow_TMDBDetail(tratktTvShowAnticipated.get(countAnticipated).getShow().getIds().getTmdb());
            }
        }
    }

    @Override
    public void displayAnticipatedMovieDetail(MovieDetailResponse movieDetailResponse) {
        countAnticipated++;
        moviesAnticipated.add(movieDetailResponse);
        if (countAnticipated == moviesAnticipatedSize) {
            traktTvAnticipatedAdapter.addItems(moviesAnticipated, null);
            moviesAnticipated.clear();
            countAnticipated = 0;
        } else {
            if (traktTvMoviesAnticipated.get(0).getMovie().getIds().getTmdb() != null) {
                homeFragmentPresenter.getTraktTvAnticipatedMovie_TMDBDetail(traktTvMoviesAnticipated.get(countAnticipated).getMovie().getIds().getTmdb());
            }
        }
    }

    @Override
    public void displayAnticipatedTvShowDetail(TvShowDetailResponse tvShowDetailResponse) {
        countAnticipated++;
        tvShowAnticipated.add(tvShowDetailResponse);
        if (countAnticipated == tvShowAnticipatedSize) {
            traktTvAnticipatedAdapter.addItems(null, tvShowAnticipated);
            tvShowAnticipated.clear();
            countAnticipated = 0;
        } else {
            if (tratktTvShowAnticipated.get(countAnticipated).getShow().getIds().getTmdb() != null) {
                homeFragmentPresenter.getTraktTvAnticipatedTvShow_TMDBDetail(tratktTvShowAnticipated.get(countAnticipated).getShow().getIds().getTmdb());
            }
        }
    }
    //**********************************************************************************************




    //************************************   TRENDING MOVIES   *************************************
    @Override
    public void displayTrendingMoviesResponse(JsonArray jsonArray) {
        Gson gson = new Gson();
        moviesTrendingSize = jsonArray.size();
        //traktTvMoviesTrending = gson.fromJson(jsonArray.toString(), ArrayList.class);
        traktTvMoviesTrending = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<TrendingMovies>>() {}.getType());
        if (traktTvMoviesTrending.get(0).getMovie().getIds().getTmdb() != null){
            homeFragmentPresenter.getTraktTvTrendingMovie_TMDBDetail(traktTvMoviesTrending.get(0).getMovie().getIds().getTmdb());
        }
    }

    @Override
    public void displayTrendingMoviesError(String erro) {
        countTrendingMovies++;
        if (countTrendingMovies == moviesTrendingSize) {
            traktTvTrendingMoviesAdapter.addItems(moviesTrending);
            moviesTrending.clear();
            countTrendingMovies = 0;
        } else {
            homeFragmentPresenter.getTraktTvTrendingMovie_TMDBDetail(traktTvMoviesTrending.get(countTrendingMovies).getMovie().getIds().getTmdb());
        }
    }

    @Override
    public void displayTrendingMovieDetail(MovieDetailResponse movieDetailResponse) {
        countTrendingMovies++;
        moviesTrending.add(movieDetailResponse);
        if (countTrendingMovies == moviesTrendingSize) {
            traktTvTrendingMoviesAdapter.addItems(moviesTrending);
            moviesTrending.clear();
            countTrendingMovies = 0;
        } else {
            if(traktTvMoviesTrending.get(countTrendingMovies).getMovie().getIds().getTmdb() != null){
                homeFragmentPresenter.getTraktTvTrendingMovie_TMDBDetail(traktTvMoviesTrending.get(countTrendingMovies).getMovie().getIds().getTmdb());
            }
        }
    }
    //**********************************************************************************************




    //************************************   TRENDING TVSHOWS   ************************************
    @Override
    public void displayTrendingTvShowsResponse(JsonArray jsonArray) {
        Gson gson = new Gson();
        tvShowsTrendingSize = jsonArray.size();
        traktTvTvShowsTrending = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<TrendingTvShow>>() {}.getType());
        homeFragmentPresenter.getTraktTvTrendingTvShow_TMDBDetail(traktTvTvShowsTrending.get(0).getShow().getIds().getTmdb());
    }

    @Override
    public void displayTrendingTvShowsError(String erro) {
        countTrendingTvShows++;
        if (countTrendingTvShows == tvShowsTrendingSize) {
            traktTvTrendingTvShowsAdapter.addItems(tvShowsTrending);
            tvShowsTrending.clear();
            countTrendingTvShows = 0;
        } else {
            homeFragmentPresenter.getTraktTvTrendingTvShow_TMDBDetail(traktTvTvShowsTrending.get(countTrendingTvShows).getShow().getIds().getTmdb());
        }
    }

    @Override
    public void displayTrendingTvShowsDetail(TvShowDetailResponse tvShowDetailResponse) {
        countTrendingTvShows++;
        tvShowsTrending.add(tvShowDetailResponse);
        if (countTrendingTvShows == tvShowsTrendingSize) {
            traktTvTrendingTvShowsAdapter.addItems(tvShowsTrending);
            tvShowsTrending.clear();
            countTrendingTvShows = 0;
        } else {
            homeFragmentPresenter.getTraktTvTrendingTvShow_TMDBDetail(traktTvTvShowsTrending.get(countTrendingTvShows).getShow().getIds().getTmdb());
        }
    }
    //**********************************************************************************************




    //****************************************   NETWORKS   ****************************************
    @Override
    public void displayNetworksMovieList(ListsResponse listsResponse, int network) {
        String TAG = "List";
        if (listsResponse != null) {
            //Log.d(TAG,listsResponse.getItems().get(1).getName());
            switch (network) {
                case 0:
                    netflixAdapter.addItems(listsResponse.getItems(), null);
                    break;
                case 1:
                    primeVideoAdapter.addItems(listsResponse.getItems(), null);
                    break;
            }
        } else {
            Log.d(TAG, "List response null");
        }
    }

    @Override
    public void displayNetworksTvShows(TvShowResponse tvShowResponse, int network) {
        String TAG = "NetflixTvShow";
        if (tvShowResponse != null) {
            //Log.d(TAG,listsResponse.getItems().get(1).getName());
            switch (network) {
                case 0:
                    netflixAdapter.addItems(null, tvShowResponse.getResults());
                    break;
                case 1:
                    primeVideoAdapter.addItems(null, tvShowResponse.getResults());
                    break;
            }
        } else {
            Log.d(TAG, "List response null");
        }
    }
    //**********************************************************************************************




    //***************************************   COLLECTIONS   **************************************
    @Override
    public void displayCollections(List<CollectionsModel> collectionsModelList) {
        collectionsAdapter.addItems(collectionsModelList);
    }
    //**********************************************************************************************




    //******************************************   PEOPLE   ****************************************
    @Override
    public void displayPeople(People people){
        peopleAdapter.addItems(people.getResults());
    }
    //**********************************************************************************************




    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }

}