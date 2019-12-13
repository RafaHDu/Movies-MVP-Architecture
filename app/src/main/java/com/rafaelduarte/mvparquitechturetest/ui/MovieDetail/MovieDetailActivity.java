package com.rafaelduarte.mvparquitechturetest.ui.MovieDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.models.Result;
import com.rafaelduarte.mvparquitechturetest.ui.MovieDetail.CommentsFragment.CommentsFragment;
import com.rafaelduarte.mvparquitechturetest.ui.MovieDetail.OverviewFragment.OverviewFragment;
import com.rafaelduarte.mvparquitechturetest.ui.MovieDetail.ReviewsFragment.ReviewsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailViewInterface, OverviewFragment.OnFragmentInteractionListener
                                                                    , ReviewsFragment.OnFragmentInteractionListener
                                                                    , CommentsFragment.OnFragmentInteractionListener{

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
    @BindView(R.id.tvGenre)
    TextView tvDescription;
    @BindView(R.id.viewPagerMovieDetail)
    ViewPager viewPagerMovieDetail;
    @BindView(R.id.tabsMovieDetail)
    TabLayout tabsMovieDetail;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.toolbarMovieDetail)
    MaterialToolbar toolbarMovieDetail;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    private int MOVIE_ID;
    public static final String EXTRA_RESULT = "movieDetailResult";
    MovieDetailPresenter movieDetailPresenter;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    String title = "";

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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPagerMovieDetail.setAdapter(mSectionsPagerAdapter);
        viewPagerMovieDetail.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabsMovieDetail));
        tabsMovieDetail.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerMovieDetail));

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbarLayout.setTitle(title);
                    toolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
                    toolbarMovieDetail.getNavigationIcon().setTint(Color.BLACK);
                    isShow = true;
                } else if (isShow) {
                    toolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    toolbarMovieDetail.getNavigationIcon().setTint(Color.WHITE);
                    isShow = false;
                }
            }
        });
    }

    public void setupMVP(){
        this.movieDetailPresenter = new MovieDetailPresenter(this, this);
    }

    public void setDisplayUI(){
        movieDetailPresenter.setUpUIInfor();
    }

    @Override
    public void displayUI(String pathPoster, String pathBG, String title, String year, String language, String rating, int id, String genres) {
        this.title = title;
        Glide.with(this).load("https://image.tmdb.org/t/p/w780/" + pathPoster).transition(DrawableTransitionOptions.withCrossFade(200)).into(ivMovieDetailPoster);
        Glide.with(this).load("https://image.tmdb.org/t/p/w1280/" + pathBG).transition(DrawableTransitionOptions.withCrossFade(200)).into(ivMovieDetailBG);
        tvMovieTitle.setText(title);
        tvRelease.setText(year);
        tvLanguage.setText(language);
        tvRating.setText(rating);
        tvDescription.setText(genres);
        MOVIE_ID = id;
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return OverviewFragment.newInstance("","");
                case 1:
                    return ReviewsFragment.newInstance("","");
                case 2:
                    return CommentsFragment.newInstance("","");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
