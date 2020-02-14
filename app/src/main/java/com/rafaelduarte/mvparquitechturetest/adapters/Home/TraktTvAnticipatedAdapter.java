package com.rafaelduarte.mvparquitechturetest.adapters.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieDetails.MovieDetailResponse;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowDetails.TvShowDetailResponse;
import com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.home.HomeFragmentPresenter;
import com.rafaelduarte.mvparquitechturetest.ui.MovieDefaultInformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TraktTvAnticipatedAdapter extends RecyclerView.Adapter<TraktTvAnticipatedAdapter.MoviesHolder> {

    private static List<MovieDetailResponse> movieList = new ArrayList<>();
    private static List<TvShowDetailResponse> tvShowList = new ArrayList<>();
    private Context context;
    private HomeFragmentPresenter homeFragmentPresenter;
    private MovieDefaultInformation movieDefaultInformation;
    private int type;

    //type     ->     0 = Movies     1 = TvShow
    public TraktTvAnticipatedAdapter(int type, List<MovieDetailResponse> movieList, List<TvShowDetailResponse> tvShowList, Context context, HomeFragmentPresenter homeFragmentPresenter) {
        this.type = type;
        TraktTvAnticipatedAdapter.movieList.addAll(movieList);
        TraktTvAnticipatedAdapter.tvShowList.addAll(tvShowList);
        this.context = context;
        this.homeFragmentPresenter = homeFragmentPresenter;
        setHasStableIds(true);
        movieDefaultInformation = new MovieDefaultInformation();
    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_movies,parent,false);
        MoviesHolder mh = new MoviesHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesHolder holder, int position) {
        holder.onBind(position);
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        if (type == 0){
            return movieList.size();
        } else {
            return tvShowList.size();
        }
    }

    public class MoviesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivPeople)
            ImageView ivMoviePoster;
        @BindView(R.id.tvAnticipatedDate)
            TextView tvAnticipatedDate;
        @BindView(R.id.tvPosterName)
            TextView tvPosterName;
        @BindView(R.id.rl_profile_rating)
            RelativeLayout rl_profile_rating;

        public MoviesHolder(View v) {
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

                //homeFragmentPresenter.onItemInteraction(movieList.get(getAdapterPosition()), ivMoviePoster);
            }
        }

        public void onBind(int position){
            if (type == 0){
                if (movieList.get(position).getReleaseDate() != null){
                    tvAnticipatedDate.setText(movieDefaultInformation.getDate(movieList.get(position).getReleaseDate()));
                } else {
                    rl_profile_rating.setVisibility(View.INVISIBLE);
                }

                if (movieList.get(position).getPosterPath() != null){
                    Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+movieList.get(position).getPosterPath())
                            .transition(DrawableTransitionOptions.withCrossFade(200))
                            .into(ivMoviePoster);
                } else {
                    if (movieList.get(position).getTitle() != null){
                        if (movieList.get(position).getTitle().equals("")){
                            tvPosterName.setText("N/A");
                        } else {
                            tvPosterName.setText(movieList.get(position).getTitle());
                        }
                        tvPosterName.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (tvShowList.get(position).getFirstAirDate() != null){
                    tvAnticipatedDate.setText(movieDefaultInformation.getDate(tvShowList.get(position).getFirstAirDate()));
                } else {
                    rl_profile_rating.setVisibility(View.INVISIBLE);
                }

                if (tvShowList.get(position).getPosterPath() != null){
                    Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+tvShowList.get(position).getPosterPath())
                            .transition(DrawableTransitionOptions.withCrossFade(200))
                            .into(ivMoviePoster);
                } else {
                    if (tvShowList.get(position).getName() != null){
                        if (tvShowList.get(position).getName().equals("")){
                            tvPosterName.setText("N/A");
                        } else {
                            tvPosterName.setText(tvShowList.get(position).getName());
                        }
                        tvPosterName.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
    }

    /*public String setGenre(int position){
        String genre = "";
        for (int x = 0; x < movieList.get(position).getGenreIds().size(); x++) {
            int genre_id = movieList.get(position).getGenreIds().get(x);
            genre += movieDefaultInformation.getMoviesGenreByID(genre_id);
            if (x != (movieList.get(position).getGenreIds().size() - 1)) {
                genre += ", ";
            }
        }
        return genre;
    }*/

    public void addItems(List<MovieDetailResponse> resultsMovies, List<TvShowDetailResponse> resultsTvShow) {
        if (type == 0){
            //Movies
            movieList.addAll(resultsMovies);
        } else {
            //TvShow
            tvShowList.addAll(resultsTvShow);
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        //MovieDetailResponse result = movieList.get(position);
        //return result.getId();
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
