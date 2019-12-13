package com.rafaelduarte.mvparquitechturetest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.models.Result;
import com.rafaelduarte.mvparquitechturetest.ui.Main.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

    private static List<Result> movieList = new ArrayList<>();
    private Context context;
    private MainPresenter mainPresenter;

    public MoviesAdapter(List<Result> movieList, Context context, MainPresenter mainPresenter) {
        MoviesAdapter.movieList.addAll(movieList);
        this.context = context;
        this.mainPresenter = mainPresenter;
        setHasStableIds(true);
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
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MoviesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvYear)
        TextView tvYear;
        @BindView(R.id.tvGenres)
        TextView tvGenres;
        @BindView(R.id.tvMovieRating)
        TextView tvMovieRating;
        @BindView(R.id.ivMoviePoster)
        ImageView ivMoviePoster;
        @BindView(R.id.ivMovieBackground)
        ImageView ivMovieBackground;

        public MoviesHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            // Attach a click listener to the entire row view. Makes onClick fun work
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mainPresenter != null){
                //Must implement OnClickListener in adapter/viewholder and call in the presenter from there.
                //OnClickListener is an interface from Android SDK, the presenter should not know anything about the Andriod SDK.
                //Presenter should be pure Java so it can be tested just by using Unit test on the JVM.
                //It shouldn't know anything about views, RecyclerView, Adapter or ViewHolder.

                //mainPresenter.onItemInteraction(getAdapterPosition());
                mainPresenter.onItemInteraction(movieList.get(getAdapterPosition()), ivMoviePoster);
            }
        }

        public void onBind(int position){
            tvTitle.setText(movieList.get(position).getTitle());
            tvMovieRating.setText(String.valueOf(movieList.get(position).getVoteAverage()));
            tvYear.setText(movieList.get(position).getReleaseDate());
            Glide.with(context).load("https://image.tmdb.org/t/p/w342/"+movieList.get(position).getPosterPath())
                    .transition(DrawableTransitionOptions.withCrossFade(200))
                    .into(ivMoviePoster);
            Glide.with(context).load("http://image.tmdb.org/t/p/w780/"+movieList.get(position).getBackdropPath())
                    .transition(DrawableTransitionOptions.withCrossFade(200))
                    .apply(new RequestOptions().transforms(new BlurTransformation(6, 3)))
                    .into(ivMovieBackground);
        }

    }

    public void addItems(List<Result> results) {
        movieList.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        Result result = movieList.get(position);
        return result.getId();
    }
}
