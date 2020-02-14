package com.rafaelduarte.mvparquitechturetest.adapters.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.models.GenresModel;
import com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.home.HomeFragmentPresenter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresHolder> {

    private static ArrayList<GenresModel> genresList = new ArrayList<>();
    private Context context;
    private HomeFragmentPresenter homeFragmentPresenter;

    public GenresAdapter(ArrayList<GenresModel> genresList, Context context, HomeFragmentPresenter homeFragmentPresenter) {
        GenresAdapter.genresList.addAll(genresList);
        this.context = context;
        this.homeFragmentPresenter = homeFragmentPresenter;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public GenresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_genres,parent,false);
        GenresHolder gh = new GenresHolder(v);
        return gh;
    }

    @Override
    public void onBindViewHolder(@NonNull GenresHolder holder, int position) {
        holder.onBind(position);
        holder.setIsRecyclable(false); //Not Recycling items
    }

    @Override
    public int getItemCount() {
        return genresList.size();
    }

    public class GenresHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvGenre)
        TextView tvGenre;
        @BindView(R.id.clGenres)
        ConstraintLayout clGenres;

        public GenresHolder(View v) {
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
                //homeFragmentPresenter.onGenreItemInteraction(genresList.get(getAdapterPosition()));
            }
        }

        public void onBind(int position){
            tvGenre.setText(genresList.get(position).getName());
            setBackgroundGradient(position, clGenres);
        }

    }

    public void addItems(ArrayList<GenresModel> results) {
        genresList.addAll(results);
        notifyDataSetChanged();
    }


    private void setBackgroundGradient(int position, ConstraintLayout clGenres){
        switch (position){
            case 0:
                //Action
                clGenres.setBackgroundResource(R.drawable.gradient1);
                break;
            case 1:
                //Adventure
                clGenres.setBackgroundResource(R.drawable.gradient2);
                break;
            case 2:
                //Animation
                clGenres.setBackgroundResource(R.drawable.gradient3);
                break;
            case 3:
                //Comedy
                clGenres.setBackgroundResource(R.drawable.gradient4);
                break;
            case 4:
                //Crime
                clGenres.setBackgroundResource(R.drawable.gradient5);
                break;
            case 5:
                //Documentary
                clGenres.setBackgroundResource(R.drawable.gradient6);
                break;
            case 6:
                //Drama
                clGenres.setBackgroundResource(R.drawable.gradient7);
                break;
            case 7:
                //Family
                clGenres.setBackgroundResource(R.drawable.gradient8);
                break;
            case 8:
                //Horror
                clGenres.setBackgroundResource(R.drawable.gradient2);
                break;
            case 9:
                //Mystery
                clGenres.setBackgroundResource(R.drawable.gradient1);
                break;
            case 10:
                //Romance
                clGenres.setBackgroundResource(R.drawable.gradient4);
                break;
            case 11:
                //Sci-Fi
                clGenres.setBackgroundResource(R.drawable.gradient8);
                break;
            case 12:
                //Thriller
                clGenres.setBackgroundResource(R.drawable.gradient3);
                break;
            case 13:
                //War
                clGenres.setBackgroundResource(R.drawable.gradient7);
                break;
            case 14:
                //Western
                clGenres.setBackgroundResource(R.drawable.gradient5);
                break;
        }
    }

}
