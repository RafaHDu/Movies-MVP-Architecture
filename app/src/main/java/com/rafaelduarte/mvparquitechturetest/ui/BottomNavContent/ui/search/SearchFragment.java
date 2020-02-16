package com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.adapters.ItemDecorators.ItemDecorator;
import com.rafaelduarte.mvparquitechturetest.adapters.Search.SearchMoviesAdapter;
import com.rafaelduarte.mvparquitechturetest.adapters.Search.SearchTvShowAdapter;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.MovieResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.TvShowResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment implements SearchFragmentInterface {

    @BindView(R.id.rvMovies)
            RecyclerView rvMovies;

    @BindView(R.id.rvTvShow)
            RecyclerView rvTvShow;

    LinearLayoutManager moviesLayoutManager, tvShowLayoutManager;

    SearchMoviesAdapter searchMoviesAdapter;
    SearchTvShowAdapter searchTvShowAdapter;

    SearchFragmentPresenter searchFragmentPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, root);

        setupMVP();
        setupViews();
        getMovies();
        getTvShows();

        return root;
    }

    public void setupMVP(){searchFragmentPresenter = new SearchFragmentPresenter(this); }

    public void setupViews(){
        searchMoviesAdapter = new SearchMoviesAdapter(new ArrayList<>(), getContext(), searchFragmentPresenter);
        rvMovies.setAdapter(searchMoviesAdapter);
        moviesLayoutManager = new LinearLayoutManager(getContext());
        moviesLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMovies.setLayoutManager(moviesLayoutManager);
        rvMovies.setHasFixedSize(true);
        rvMovies.setItemAnimator(null);
        // TOP - BOTTOM - LEFT - RIGHT
        rvMovies.addItemDecoration(new ItemDecorator(0, 0, 32, 32));

        searchTvShowAdapter = new SearchTvShowAdapter(new ArrayList<>(), getContext(), searchFragmentPresenter);
        rvTvShow.setAdapter(searchTvShowAdapter);
        tvShowLayoutManager = new LinearLayoutManager(getContext());
        tvShowLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTvShow.setLayoutManager(tvShowLayoutManager);
        rvTvShow.setHasFixedSize(true);
        rvTvShow.setItemAnimator(null);
        // TOP - BOTTOM - LEFT - RIGHT
        rvTvShow.addItemDecoration(new ItemDecorator(0, 0, 32, 32));
    }

    private void getMovies(){searchFragmentPresenter.getMovies();}

    private void getTvShows(){searchFragmentPresenter.getTvShows();}


    @Override
    public void displayMovies(MovieResponse movieResponse) {
        searchMoviesAdapter.addItems(movieResponse.getResults());
    }

    @Override
    public void displayTvShows(TvShowResponse tvShowResponse) {
        searchTvShowAdapter.addItems(tvShowResponse.getResults());
    }

    @Override
    public void displayError(String s) {}

}