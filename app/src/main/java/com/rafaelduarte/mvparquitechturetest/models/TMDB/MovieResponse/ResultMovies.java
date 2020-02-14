package com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResultMovies implements Parcelable {

    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    public ResultMovies() {}

    public ResultMovies(Double popularity, Integer voteCount, Boolean video, String posterPath, Integer id, Boolean adult, String backdropPath, String originalLanguage, String originalTitle, List<Integer> genreIds, String title, Double voteAverage, String overview, String releaseDate) {
        setPopularity(popularity);
        setVoteCount(voteCount);
        setVideo(video);
        setPosterPath(posterPath);
        setId(id);
        setAdult(adult);
        setBackdropPath(backdropPath);
        setOriginalLanguage(originalLanguage);
        setOriginalTitle(originalTitle);
        setGenreIds(genreIds);
        setTitle(title);
        setVoteAverage(voteAverage);
        setOverview(overview);
        setReleaseDate(releaseDate);
    }


    public ResultMovies(Parcel parcel) {
        //Corresponde ao Deserialize **Tem que estar na mesma ordem do Serialize**
        this.posterPath = parcel.readString();
        this.id = parcel.readInt();
        this.backdropPath = parcel.readString();
        this.originalLanguage = parcel.readString();
        this.title = parcel.readString();
        this.voteAverage = parcel.readDouble();
        this.releaseDate = parcel.readString();

        this.genreIds = new ArrayList<Integer>();
        parcel.readList(this.genreIds, Integer.class.getClassLoader());
        /*Exemplo na lista
        this.genreIds = new ArrayList<Integer>();
        parcel.readList(this.genreIds, Integer.class.getClassLoader());*/
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Corresponde ao Serialize **Tem que estar na mesma ordem do Deserialize**
        //pega todas as entidades acima e escreve no Parcel (que é o objecto usado no transporte)
        //'flags' serve para tratar os dados de forma especial ao enviar com valores 0 e 1.

        //Exemplo de boolean --> dest.writeByte((byte) (video ? 1 : 0));
        //Exemplo de Lista --> dest.writeList(genreIds);
        dest.writeString(posterPath);
        dest.writeInt(id);
        dest.writeString(backdropPath);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeDouble(voteAverage);
        dest.writeString(releaseDate);
        dest.writeList(genreIds);
    }


    public static final Parcelable.Creator<ResultMovies> CREATOR = new Parcelable.Creator<ResultMovies>(){
        @Override
        public ResultMovies createFromParcel(Parcel source) {
            //pega no Parcel (que foi preenchido no writeToParcel) e instanceia o objecto ResultTvShow (com o construtor do Deserialize)
            return new ResultMovies(source);
        }

        @Override
        public ResultMovies[] newArray(int size) {
            return new ResultMovies[size];
        }
    };

}
