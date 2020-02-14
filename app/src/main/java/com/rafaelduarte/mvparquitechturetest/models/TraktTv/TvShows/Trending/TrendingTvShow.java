package com.rafaelduarte.mvparquitechturetest.models.TraktTv.TvShows.Trending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rafaelduarte.mvparquitechturetest.models.TraktTv.TvShows.Anticipated.Show;

public class TrendingTvShow {

    @SerializedName("watchers")
    @Expose
    private Integer watchers;
    @SerializedName("show")
    @Expose
    private Show show;

    public Integer getWatchers() {
        return watchers;
    }

    public void setWatchers(Integer watchers) {
        this.watchers = watchers;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

}
