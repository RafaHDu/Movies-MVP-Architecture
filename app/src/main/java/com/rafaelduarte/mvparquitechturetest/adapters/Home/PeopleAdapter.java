package com.rafaelduarte.mvparquitechturetest.adapters.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.rafaelduarte.mvparquitechturetest.R;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.People.People;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.People.Result;
import com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.home.HomeFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    private static List<Result> peopleList = new ArrayList<>();
    private Context context;
    private HomeFragmentPresenter homeFragmentPresenter;

    public PeopleAdapter(List<Result> people, Context context, HomeFragmentPresenter homeFragmentPresenter) {
        PeopleAdapter.peopleList = people;
        this.context = context;
        this.homeFragmentPresenter = homeFragmentPresenter;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_people,parent,false);
        PeopleViewHolder mh = new PeopleViewHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        holder.onBind(position);
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.ivPeople)
        ImageView ivPeople;
        @BindView(R.id.tvPeopleName)
        TextView tvPeopleName;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void onBind(int position){

            if(peopleList.get(position).getProfilePath() != null){
                Glide.with(context).load("https://image.tmdb.org/t/p/w300/"+peopleList.get(position).getProfilePath())
                        .transition(DrawableTransitionOptions.withCrossFade(200))
                        .into(ivPeople);
            }

            if (!peopleList.get(position).getName().equals("")){
                tvPeopleName.setText(peopleList.get(position).getName());
            } else {
                tvPeopleName.setText("N/A");
            }

        }

    }

    public void addItems(List<Result> peopleResult) {
        peopleList.addAll(peopleResult);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        Result result = peopleList.get(position);
        return result.getId();
    }

}
