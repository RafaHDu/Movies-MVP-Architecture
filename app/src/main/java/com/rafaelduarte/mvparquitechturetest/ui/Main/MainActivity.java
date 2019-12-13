package com.rafaelduarte.mvparquitechturetest.ui.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.adapters.MoviesAdapter;
import com.rafaelduarte.mvparquitechturetest.models.MovieResponse;
import com.rafaelduarte.mvparquitechturetest.models.Result;
import com.rafaelduarte.mvparquitechturetest.ui.MovieDetail.MovieDetailActivity;

import java.sql.ResultSet;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainViewInterface, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.rvMovies)
            RecyclerView rvMovies;
    @BindView(R.id.swipeRefresh)
            SwipeRefreshLayout swipeRefresh;
    public static final String EXTRA_RESULT = "movieDetailResult";
    LinearLayoutManager layoutManager;
    MoviesAdapter adapter;
    MainPresenter mainPresenter;
    boolean isScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupMVP();
        setupViews();
        getMovieList();

        rvMovies.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //fica True quando é dado Scroll de modo a poder chamar o Data Fetch e se assim for é retomado a False
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (isScrolling && ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 4 && firstVisibleItemPosition >= 0 && totalItemCount >= 10)) {
                //if (isScrolling && (visibleItemCount + firstVisibleItemPosition == totalItemCount)) {
                    mainPresenter.onLoadNextPage();
                }
            }
        });

    }

    private void setupMVP() {
        mainPresenter = new MainPresenter(this);
    }

    private void setupViews(){
        swipeRefresh.setOnRefreshListener(this);
        //rvMovies.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MoviesAdapter(new ArrayList<>(), MainActivity.this, mainPresenter);
        rvMovies.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setHasFixedSize(true);
        rvMovies.setItemAnimator(null);
    }

    private void getMovieList() { mainPresenter.getMovies(); }

    @Override
    public void showToast(String s) {
        Toast.makeText(MainActivity.this, s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayMovies(MovieResponse movieResponse) {
        String TAG = "MainActivity";
        if(movieResponse!=null) {
            Log.d(TAG,movieResponse.getResults().get(1).getTitle());
            //adapter = new MoviesAdapter(movieResponse.getResults(), MainActivity.this, mainPresenter);
            adapter.addItems(movieResponse.getResults());
            /*if (mainPresenter.mCurrentPage != 1){
                adapter.notifyDataSetChanged();
            }*/
        }else{
            Log.d(TAG,"Movies response null");
        }
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }

    @Override
    public void openMovieDetailActivity(Result result, ImageView imageView) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra(EXTRA_RESULT, result);
        //create the animation
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "sharedName");
        startActivity(intent, options.toBundle());
        //startActivity(intent);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
    }

}
