package com.example.myfavouritesapp.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.myfavouritesapp.service.ApiResponse;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Film implements Parcelable {

    @SerializedName("id")
    private int theIds;

    @SerializedName("homepage")
    private String homePage;

    @SerializedName("title")
    private String filmTitle;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("overview")
    private String overView;

    @SerializedName("tagline")
    private String tagLine;

    @SerializedName("credits")
    private ApiResponse pemain;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private float rating;

    @SerializedName("revenue")
    private int filmIncome;

    @SerializedName("genres")
    private List<Genre> genresList;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("runtime")
    private int runTime;

    protected Film(Parcel in) {
        theIds = in.readInt();
        homePage = in.readString();
        filmTitle = in.readString();
        voteCount = in.readInt();
        overView = in.readString();
        tagLine = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        rating = in.readFloat();
        filmIncome = in.readInt();
        genresList = in.createTypedArrayList(Genre.CREATOR);
        originalLanguage = in.readString();
        runTime = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(theIds);
        dest.writeString(homePage);
        dest.writeString(filmTitle);
        dest.writeInt(voteCount);
        dest.writeString(overView);
        dest.writeString(tagLine);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeFloat(rating);
        dest.writeInt(filmIncome);
        dest.writeTypedList(genresList);
        dest.writeString(originalLanguage);
        dest.writeInt(runTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getTheIds() {
        return theIds;
    }

    public void setTheIds(int theIds) {
        this.theIds = theIds;
    }

    public int getFilmIncome() {
        return filmIncome;
    }

    public void setFilmIncome(int filmIncome) {
        this.filmIncome = filmIncome;
    }


    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public ApiResponse getPemain() {
        return pemain;
    }

    public void setPemain(ApiResponse pemain) {
        this.pemain = pemain;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReleaseDate(Context context) {
        String input = "yyyy-MM-dd";
        if (releaseDate != null && !releaseDate.isEmpty()) {
            try {
                SimpleDateFormat inputF = new SimpleDateFormat(input, java.util.Locale.US);
                Date date = inputF.parse(releaseDate);
                return DateFormat.getDateInstance().format(date);
            } catch (ParseException e) {
                Log.e(TAG, "error to parse: " + releaseDate);
            }

        } else {
            releaseDate = "No Data";
        }
        return releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Genre> getGenresList() {
        return genresList;
    }

    public void setGenresList(List<Genre> genresList) {
        this.genresList = genresList;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Film() {
    }



}
