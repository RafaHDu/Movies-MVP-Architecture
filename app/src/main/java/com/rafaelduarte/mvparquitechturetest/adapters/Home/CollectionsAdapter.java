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
import com.rafaelduarte.mvparquitechturetest.models.CollectionsModel;
import com.rafaelduarte.mvparquitechturetest.ui.BottomNavContent.ui.home.HomeFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder> {

    private List<CollectionsModel> collectionList = new ArrayList<>();
    private Context context;
    private HomeFragmentPresenter homeFragmentPresenter;

    public CollectionsAdapter(List<CollectionsModel> collectionList, Context context, HomeFragmentPresenter homeFragmentPresenter){
        collectionList.addAll(collectionList);
        this.context = context;
        this.homeFragmentPresenter = homeFragmentPresenter;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CollectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_collections,parent,false);
        CollectionsViewHolder mh = new CollectionsViewHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionsViewHolder holder, int position) {
        holder.onBind(position);
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public class CollectionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivCollection)
        ImageView ivCollection;
        @BindView(R.id.tvCollectionName)
        TextView tvCollectionName;

        public CollectionsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
            Glide.with(context).load("https://image.tmdb.org/t/p/w780/"+collectionList.get(position).getUrl())
                    .transition(DrawableTransitionOptions.withCrossFade(200))
                    .into(ivCollection);
            tvCollectionName.setText(collectionList.get(position).getTitle());
        }

    }

    public void addItems(List<CollectionsModel> collectionsModels) {
        collectionList.addAll(collectionsModels);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        CollectionsModel result = collectionList.get(position);
        return result.getId();
    }

}
