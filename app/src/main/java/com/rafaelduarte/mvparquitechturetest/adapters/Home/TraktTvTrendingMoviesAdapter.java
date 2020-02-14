package com.rafaelduarte.mvparquitechturetest.adapters.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieDetails.MovieDetailResponse;
import com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.home.HomeFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TraktTvTrendingMoviesAdapter extends RecyclerView.Adapter<TraktTvTrendingMoviesAdapter.TrendingMoviesHolder> {

    private static List<MovieDetailResponse> trendingMovies = new ArrayList<>();
    private Context context;
    private HomeFragmentPresenter homeFragmentPresenter;

    public TraktTvTrendingMoviesAdapter(List<MovieDetailResponse> trendingMovies, Context context, HomeFragmentPresenter homeFragmentPresenter) {
        TraktTvTrendingMoviesAdapter.trendingMovies.addAll(trendingMovies);
        this.context = context;
        this.homeFragmentPresenter = homeFragmentPresenter;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public TrendingMoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_movie_tv_show,parent,false);
        TrendingMoviesHolder mh = new TrendingMoviesHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingMoviesHolder holder, int position) {
        holder.onBind(position);
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return trendingMovies.size();
    }

    public class TrendingMoviesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivMovieTvHome)
            RoundedImageView ivMovieTvHome;
        @BindView(R.id.tvMovieTvRatingHome)
            TextView tvMovieTvRatingHome;
        @BindView(R.id.tvPosterName)
            TextView tvPosterName;

        public TrendingMoviesHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            // Attach a click listener to the entire row view. Makes onClick fun work
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (homeFragmentPresenter != null){
                //Must implement OnClickListener in adapter/viewholder and call in the presenter from there.
                //OnClickListener is an interface from Android SDK, the presenter should not know anything about the Andriod SDK.
                //Presenter should be pure Java so it can be tested just by using Unit test on the JVM.
                //It shouldn't know anything about views, RecyclerView, Adapter or ViewHolder.

                //mainPresenter.onItemInteraction(getAdapterPosition());

                //homeFragmentPresenter.onItemInteraction(trendingMovies.get(getAdapterPosition()), ivMovieTvHome);
            }
        }

        public void onBind(int position){
            //tvMovieTvRatingHome.setText(String.valueOf(trendingMovieList.get(position).getVoteAverage()));
            if (trendingMovies.get(position).getVoteAverage() != 0){
                tvMovieTvRatingHome.setText(String.valueOf(trendingMovies.get(position).getVoteAverage()));
            } else {
                tvMovieTvRatingHome.setText("N/A");
            }

            if(trendingMovies.get(position).getPosterPath() != null){
                Glide.with(context).load("https://image.tmdb.org/t/p/w342/"+trendingMovies.get(position).getPosterPath())
                        .transition(DrawableTransitionOptions.withCrossFade(200))
                        .into(ivMovieTvHome);
            } else {
                if (trendingMovies.get(position).getTitle().equals("")){
                    tvPosterName.setText(trendingMovies.get(position).getTitle());
                }
            }
        }

    }

    public void addItems(List<MovieDetailResponse> trendingMoviess) {
        trendingMovies.addAll(trendingMoviess);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        MovieDetailResponse result = trendingMovies.get(position);
        return result.getId();
    }

}
