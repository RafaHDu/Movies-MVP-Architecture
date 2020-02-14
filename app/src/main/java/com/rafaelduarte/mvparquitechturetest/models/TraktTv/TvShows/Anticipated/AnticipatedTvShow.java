package com.rafaelduarte.mvparquitechturetest.models.TraktTv.TvShows.Anticipated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnticipatedTvShow {

    @SerializedName("list_count")
    @Expose
    private Integer listCount;
    @SerializedName("show")
    @Expose
    private Show show;

    public Integer getListCount() {
        return listCount;
    }

    public void setListCount(Integer listCount) {
        this.listCount = listCount;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

}
