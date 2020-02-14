package com.rafaelduarte.mvparquitechturetest.models.TraktTv.TvShows.Anticipated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ids {

    @SerializedName("trakt")
    @Expose
    private Integer trakt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("tvdb")
    @Expose
    private Integer tvdb;
    @SerializedName("imdb")
    @Expose
    private String imdb;
    @SerializedName("tmdb")
    @Expose
    private Integer tmdb;
    @SerializedName("tvrage")
    @Expose
    private Object tvrage;

    public Integer getTrakt() {
        return trakt;
    }

    public void setTrakt(Integer trakt) {
        this.trakt = trakt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getTvdb() {
        return tvdb;
    }

    public void setTvdb(Integer tvdb) {
        this.tvdb = tvdb;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public Integer getTmdb() {
        return tmdb;
    }

    public void setTmdb(Integer tmdb) {
        this.tmdb = tmdb;
    }

    public Object getTvrage() {
        return tvrage;
    }

    public void setTvrage(Object tvrage) {
        this.tvrage = tvrage;
    }

}
