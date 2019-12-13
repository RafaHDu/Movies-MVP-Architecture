package com.rafaelduarte.mvparquitechturetest.ui.MovieDetail;

import android.content.Context;
import android.content.res.Configuration;

import com.rafaelduarte.mvparquitechturetest.models.Result;

import java.util.Locale;

public class MovieDetailPresenter {

    MovieDetailViewInterface mdvi;
    private Result result;
    private Context context;

    public MovieDetailPresenter(MovieDetailViewInterface mdvi, Context context){
        this.mdvi = mdvi;
        this.context = context;
    }

    public void setResult(Result result){
        this.result = result;
    }

    public void setUpUIInfor(){

        //Year
        String releaseDate = String.valueOf(result.getReleaseDate());
        String year = "";
        if (!releaseDate.isEmpty()) {
            year = releaseDate.substring(0, 4);
        }

        //language
        String language = "";
        Locale loc = new Locale(result.getOriginalLanguage());
        Configuration config = new Configuration();
        config.locale = loc;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        //TODO: TENHO DE MUDAR ISTO PARA QUANDO PODER MUDAR AS LINGUAGENS  aqui no 'en'
        Locale locale = new Locale("en");
        language = loc.getDisplayLanguage(locale);

        //genre
        String genre = "";
        for (int x = 0; x < result.getGenreIds().size(); x++) {
            int genre_id = result.getGenreIds().get(x);
            genre += getMoviesGenreByID(genre_id);
            if (x != (result.getGenreIds().size() - 1)) {
                genre += ", ";
            }
        }

        mdvi.displayUI(result.getPosterPath(),
                result.getBackdropPath(),
                result.getTitle(),
                year,
                language,
                String.valueOf(result.getVoteAverage()),
                result.getId(),
                genre);
    }

    public String getMoviesGenreByID(int ID) {
        String genre = "";

        switch (ID) {
            case 28:
                genre = "Action";
                break;
            case 12:
                genre = "Adventure";
                break;
            case 16:
                genre = "Animation";
                break;
            case 35:
                genre = "Comedy";
                break;
            case 80:
                genre = "Crime";
                break;
            case 99:
                genre = "Documentary";
                break;
            case 18:
                genre = "Drama";
                break;
            case 10751:
                genre = "Family";
                break;
            case 14:
                genre = "Fantasy";
                break;
            case 36:
                genre = "History";
                break;
            case 27:
                genre = "Horror";
                break;
            case 10402:
                genre = "Music";
                break;
            case 9648:
                genre = "Mystery";
                break;
            case 10749:
                genre = "Romance";
                break;
            case 878:
                genre = "Science Fiction";
                break;
            case 10770:
                genre = "TV Movie";
                break;
            case 53:
                genre = "Thriller";
                break;
            case 10752:
                genre = "War";
                break;
            case 37:
                genre = "Western";
                break;
        }

        return genre;
    }

}
