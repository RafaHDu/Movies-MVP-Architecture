package com.rafaelduarte.mvparquitechturetest.ui;

import com.rafaelduarte.mvparquitechturetest.models.GenresModel;
import com.rafaelduarte.mvparquitechturetest.models.TMDB.MovieResponse.ResultMovies;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieDefaultInformation {

    Locale locale = new Locale("en");

    //Genre
    public String getGenre(int position, List<ResultMovies> movieList){
        String genre = "";
        for (int x = 0; x < movieList.get(position).getGenreIds().size(); x++) {
            int genre_id = movieList.get(position).getGenreIds().get(x);
            genre += getMoviesGenreByID(genre_id);
            if (x != (movieList.get(position).getGenreIds().size() - 1)) {
                genre += ", ";
            }
        }
        return genre;
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

    //Year
    public String getYear(String releaseDate){
        String year = "";
        if (!releaseDate.isEmpty()) {
            year = releaseDate.substring(0, 4);
        }
        return year;
    }

    //Date
    public String getDate(String releaseDate){
        String returnDate = "";
        if (!releaseDate.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(releaseDate);
            } catch (ParseException e) {
            }
            returnDate = DateFormat.getDateInstance(DateFormat.LONG, locale).format(date);
        }
        return returnDate;
    }




    //**********************************   GENRES -- NOT USING   **********************************
    ArrayList<GenresModel> genres = new ArrayList<GenresModel>();
    public void onGenreItemInteraction(GenresModel genresModel){
        //homeFragmentInterface.openGenreActivity(genresModel);
    }

    public void getGenres(){
        generateGenresList();
    }

    private void generateGenresList(){
        for (int i = 0; i <= 14; i++){
            genres.add(getGenre(i));
        }
        //homeFragmentInterface.displayGenres(genres);
    }

    private GenresModel getGenre(int position){
        GenresModel genresModel = new GenresModel("", 0, 0);
        switch (position){
            case 0:
                genresModel.setName("Action");
                genresModel.setMovieID(28);
                genresModel.setTvShowID(10759);
                break;
            case 1:
                genresModel.setName("Adventure");
                genresModel.setMovieID(12);
                genresModel.setTvShowID(10759);
                break;
            case 2:
                genresModel.setName("Animation");
                genresModel.setMovieID(16);
                genresModel.setTvShowID(16);
                break;
            case 3:
                genresModel.setName("Comedy");
                genresModel.setMovieID(35);
                genresModel.setTvShowID(35);
                break;
            case 4:
                genresModel.setName("Crime");
                genresModel.setMovieID(80);
                genresModel.setTvShowID(80);
                break;
            case 5:
                genresModel.setName("Documentary");
                genresModel.setMovieID(99);
                genresModel.setTvShowID(99);
                break;
            case 6:
                genresModel.setName("Drama");
                genresModel.setMovieID(18);
                genresModel.setTvShowID(18);
                break;
            case 7:
                genresModel.setName("Family");
                genresModel.setMovieID(10751);
                genresModel.setTvShowID(10751);
                break;
            case 8:
                genresModel.setName("Horror");
                genresModel.setMovieID(27);
                genresModel.setTvShowID(27);
                break;
            case 9:
                genresModel.setName("Mystery");
                genresModel.setMovieID(9648);
                genresModel.setTvShowID(9648);
                break;
            case 10:
                genresModel.setName("Romance");
                genresModel.setMovieID(10749);
                genresModel.setTvShowID(10749);
                break;
            case 11:
                genresModel.setName("Sci-Fi");
                genresModel.setMovieID(878);
                genresModel.setTvShowID(10765);
                break;
            case 12:
                genresModel.setName("Thriller");
                genresModel.setMovieID(53);
                genresModel.setTvShowID(53);
                break;
            case 13:
                genresModel.setName("War");
                genresModel.setMovieID(10752);
                genresModel.setTvShowID(10768);
                break;
            case 14:
                genresModel.setName("Western");
                genresModel.setMovieID(37);
                genresModel.setTvShowID(37);
                break;
        }
        return genresModel;
    }
    //**********************************************************************************************

}
