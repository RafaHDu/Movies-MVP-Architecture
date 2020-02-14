package com.rafaelduarte.mvparquitechturetest.adapters.Home;

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
import com.rafaelduarte.mvparquitechturetest.models.TMDB.ListsResponse.Item;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.TvShowResponse.ResultTvShow;
import com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.home.HomeFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrimeVideoAdapter extends RecyclerView.Adapter<PrimeVideoAdapter.PrimeVideoHolder> {

    private static List<Item> listPrimeVideo = new ArrayList<>();
    private static List<ResultTvShow> primeVideoTvShows = new ArrayList<>();
    private Context context;
    private HomeFragmentPresenter homeFragmentPresenter;
    private int type;

    //type     ->     0 = Movies     1 = TvShow
    public PrimeVideoAdapter(int type, List<Item> listPrimeVideo, List<ResultTvShow> primeVideoTvShows, Context context, HomeFragmentPresenter homeFragmentPresenter) {
        this.type = type;
        PrimeVideoAdapter.listPrimeVideo = listPrimeVideo;
        PrimeVideoAdapter.primeVideoTvShows = primeVideoTvShows;
        this.context = context;
        this.homeFragmentPresenter = homeFragmentPresenter;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PrimeVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie_tv_show, parent, false);
        PrimeVideoAdapter.PrimeVideoHolder primeVideoHolder = new PrimeVideoAdapter.PrimeVideoHolder(view);
        return primeVideoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrimeVideoHolder holder, int position) {
        holder.onBind(position);
        holder.setIsRecyclable(false); //Not Recycling items
    }

    @Override
    public int getItemCount() {
        if(type == 0){
            return listPrimeVideo.size();
        } else {
            return primeVideoTvShows.size();
        }
    }

    public class PrimeVideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.ivMovieTvHome)
            RoundedImageView ivMovieTvHome;
        @BindView(R.id.tvMovieTvRatingHome)
            TextView tvMovieTvRatingHome;

        public PrimeVideoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

                homeFragmentPresenter.onItemInteractionList(listPrimeVideo.get(getAdapterPosition()), ivMovieTvHome);
            }
        }

        public void onBind(int position){
            if(type == 0){
                tvMovieTvRatingHome.setText(String.valueOf(listPrimeVideo.get(position).getVoteAverage()));
                Glide.with(context).load("https://image.tmdb.org/t/p/w342/"+listPrimeVideo.get(position).getPosterPath())
                        .transition(DrawableTransitionOptions.withCrossFade(200))
                        .into(ivMovieTvHome);
            } else {
                tvMovieTvRatingHome.setText(String.valueOf(primeVideoTvShows.get(position).getVoteAverage()));
                Glide.with(context).load("https://image.tmdb.org/t/p/w342/"+primeVideoTvShows.get(position).getPosterPath())
                        .transition(DrawableTransitionOptions.withCrossFade(200))
                        .into(ivMovieTvHome);
            }
        }

    }

    public void addItems(List<Item> results, List<ResultTvShow> tvshowResults) {
        if (type == 0){
            listPrimeVideo.addAll(results);
        } else {
            primeVideoTvShows.addAll(tvshowResults);
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
