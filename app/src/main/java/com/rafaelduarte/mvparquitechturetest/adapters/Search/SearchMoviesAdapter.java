package com.rafaelduarte.mvparquitechturetest.adapters.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.ResultMovies;
import com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.search.SearchFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchMoviesAdapter extends RecyclerView.Adapter<SearchMoviesAdapter.SearchViewHolder> {

    private static List<ResultMovies> moviesList = new ArrayList<>();
    private Context context;
    private SearchFragmentPresenter searchFragmentPresenter;

    public SearchMoviesAdapter(List<ResultMovies> moviesList, Context context, SearchFragmentPresenter searchFragmentPresenter) {
        SearchMoviesAdapter.moviesList = moviesList;
        this.context = context;
        this.searchFragmentPresenter = searchFragmentPresenter;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_movie_tv_show,parent,false);
        SearchViewHolder mh = new SearchViewHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.onBind(position);
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.ivMovieTvHome)
        RoundedImageView ivMovieTvHome;
        @BindView(R.id.tvMovieTvRatingHome)
        TextView tvMovieTvRatingHome;
        @BindView(R.id.tvPosterName)
        TextView tvPosterName;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // Attach a click listener to the entire row view. Makes onClick fun work
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void onBind(int position){
            //tvMovieTvRatingHome.setText(String.valueOf(trendingMovieList.get(position).getVoteAverage()));
            if (moviesList.get(position).getVoteAverage() != 0){
                tvMovieTvRatingHome.setText(String.valueOf(moviesList.get(position).getVoteAverage()));
            } else {
                tvMovieTvRatingHome.setText("N/A");
            }

            if(moviesList.get(position).getPosterPath() != null){
                Glide.with(context).load("https://image.tmdb.org/t/p/w342/"+moviesList.get(position).getPosterPath())
                        .transition(DrawableTransitionOptions.withCrossFade(200))
                        .into(ivMovieTvHome);
            } else {
                if (moviesList.get(position).getTitle().equals("")){
                    tvPosterName.setText(moviesList.get(position).getTitle());
                }
            }
        }

    }

    public void addItems(List<ResultMovies> ResultMoviess) {
        moviesList.addAll(ResultMoviess);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        ResultMovies result = moviesList.get(position);
        return result.getId();
    }

}
