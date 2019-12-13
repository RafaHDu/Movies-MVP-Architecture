package com.rafaelduarte.mvparquitechturetest.ui.MovieDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.models.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailViewInterface {

    @BindView(R.id.ivMovieDetailPoster)
    ImageView ivMovieDetailPoster;
    @BindView(R.id.ivMovieDetailBG)
    ImageView ivMovieDetailBG;
    @BindView(R.id.tvMovieTitle)
    TextView tvMovieTitle;
    @BindView(R.id.tvRelease)
    TextView tvRelease;
    @BindView(R.id.tvLanguage)
    TextView tvLanguage;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    private int MOVIE_ID;
    public static final String EXTRA_RESULT = "movieDetailResult";
    MovieDetailPresenter movieDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        Result result = intent.getParcelableExtra(EXTRA_RESULT);
        setupMVP();
        movieDetailPresenter.setResult(result);
        setDisplayUI();

    }

    public void setupMVP(){
        this.movieDetailPresenter = new MovieDetailPresenter(this);
    }

    public void setDisplayUI(){
        movieDetailPresenter.setUpUIInfor();
    }

    @Override
    public void displayUI(String pathPoster, String pathBG, String title, String year, String language, String rating, String overview, int id) {
        Glide.with(this).load("https://image.tmdb.org/t/p/w780/" + pathPoster).transition(DrawableTransitionOptions.withCrossFade(200)).into(ivMovieDetailPoster);
        Glide.with(this).load("https://image.tmdb.org/t/p/w1280/" + pathBG).transition(DrawableTransitionOptions.withCrossFade(200)).into(ivMovieDetailBG);
        tvMovieTitle.setText(title);tvRelease.setText(year);tvLanguage.setText(language);tvRating.setText(rating);tvDescription.setText(overview);
        MOVIE_ID = id;
    }

}
