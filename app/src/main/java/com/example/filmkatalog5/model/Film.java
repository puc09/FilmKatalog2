package com.example.filmkatalog5.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.filmkatalog5.data.database.DatabaseContract;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.FILM_TITLE;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.FIRST_DATE;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.KEY_ID;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.LANGUAGE;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.POSTER;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.RATING;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.RELEASE_DATE;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.SINOPSIS;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.TV_TITLE;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.VOTE;

@Entity(tableName = "favorite")
public class Film implements Parcelable {

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
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int theIds;
    @Ignore
    @SerializedName("episode_run_time")
    private List<Integer> epsRuntime;
    @Ignore
    @SerializedName("homepage")
    private String homePage;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String filmTitle;
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int voteCount;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overView;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String originalName;
    @Ignore
    @SerializedName("tagline")
    private String tagLine;
    @Ignore
    @SerializedName("credits")
    private ApiResponse pemain;
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    private String firstDate;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    @Ignore
    @SerializedName("popularity")
    private float popCount;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private float rating;
    @Ignore
    @SerializedName("revenue")
    private int filmIncome;
    @Ignore
    @SerializedName("genres")
    private List<Genre> genresList;
    @ColumnInfo(name = "bahasa")
    @SerializedName("original_language")
    private String originalLanguage;
    @Ignore
    @SerializedName("runtime")
    private int runTime;
    @Ignore
    @SerializedName("number_of_episodes")
    private int epsCount;
    @Ignore
    @SerializedName("number_of_seasons")
    private int seasCount;
    @Ignore
    @SerializedName("networks")
    private List<Networks> netStation;
    @Ignore
    private long ids;
    @Ignore
    private int gbrPoster;
    @Ignore
    private String season;
    @Ignore
    private String reVenues;
    @ColumnInfo(name = "isMovie")
    private Boolean isMovie;
    @ColumnInfo(name = "isTv")
    private Boolean isTv;

    protected Film(Parcel in) {
        theIds = in.readInt();
        homePage = in.readString();
        filmTitle = in.readString();
        voteCount = in.readInt();
        overView = in.readString();
        originalName = in.readString();
        tagLine = in.readString();
        firstDate = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        popCount = in.readFloat();
        rating = in.readFloat();
        filmIncome = in.readInt();
        genresList = in.createTypedArrayList(Genre.CREATOR);
        originalLanguage = in.readString();
        runTime = in.readInt();
        epsCount = in.readInt();
        seasCount = in.readInt();
        netStation = in.createTypedArrayList(Networks.CREATOR);
        ids = in.readLong();
        gbrPoster = in.readInt();
        season = in.readString();
        reVenues = in.readString();
        byte tmpIsMovie = in.readByte();
        isMovie = tmpIsMovie == 0 ? null : tmpIsMovie == 1;
        byte tmpIsTv = in.readByte();
        isTv = tmpIsTv == 0 ? null : tmpIsTv == 1;
    }

    public Film() {
    }

    public Film(Cursor cursor) {
        this.theIds = DatabaseContract.getColumnInt(cursor, KEY_ID);
        this.filmTitle = DatabaseContract.getColumnString(cursor, FILM_TITLE);
        this.voteCount = DatabaseContract.getColumnInt(cursor, VOTE);
        this.overView = DatabaseContract.getColumnString(cursor, SINOPSIS);
        this.originalName = DatabaseContract.getColumnString(cursor, TV_TITLE);
        this.firstDate = DatabaseContract.getColumnString(cursor, FIRST_DATE);
        this.releaseDate = DatabaseContract.getColumnString(cursor, RELEASE_DATE);
        this.posterPath = DatabaseContract.getColumnString(cursor, POSTER);
        this.rating = DatabaseContract.getColumnFloat(cursor, RATING);
        this.originalLanguage = DatabaseContract.getColumnString(cursor, LANGUAGE);

    }

    public static Film fromContentValues(ContentValues values) {
        Film film = new Film();

        if (values.containsKey("id")) film.setTheIds(values.getAsInteger("id"));
        if (values.containsKey("title")) film.setFilmTitle(values.getAsString("title"));
        if (values.containsKey("vote_count")) film.setVoteCount(values.getAsInteger("vote_count"));
        if (values.containsKey("overview")) film.setOverView(values.getAsString("overview"));
        if (values.containsKey("name")) film.setOriginalName(values.getAsString("name"));
        if (values.containsKey("first_air_date"))
            film.setFirstDate(values.getAsString("first_air_date"));
        if (values.containsKey("release_date"))
            film.setReleaseDate(values.getAsString("release_date"));
        if (values.containsKey("poster_path"))
            film.setPosterPath(values.getAsString("poster_path"));
        if (values.containsKey("vote_average")) film.setRating(values.getAsFloat("vote_average"));
        if (values.containsKey("bahasa")) film.setOriginalLanguage(values.getAsString("bahasa"));

        return film;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(theIds);
        dest.writeString(homePage);
        dest.writeString(filmTitle);
        dest.writeInt(voteCount);
        dest.writeString(overView);
        dest.writeString(originalName);
        dest.writeString(tagLine);
        dest.writeString(firstDate);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeFloat(popCount);
        dest.writeFloat(rating);
        dest.writeInt(filmIncome);
        dest.writeTypedList(genresList);
        dest.writeString(originalLanguage);
        dest.writeInt(runTime);
        dest.writeInt(epsCount);
        dest.writeInt(seasCount);
        dest.writeTypedList(netStation);
        dest.writeLong(ids);
        dest.writeInt(gbrPoster);
        dest.writeString(season);
        dest.writeString(reVenues);
        dest.writeByte((byte) (isMovie == null ? 0 : isMovie ? 1 : 2));
        dest.writeByte((byte) (isTv == null ? 0 : isTv ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Boolean getMovie() {
        return isMovie;
    }

    public void setMovie(Boolean movie) {
        isMovie = movie;
    }

    public Boolean getTv() {
        return isTv;
    }

    public void setTv(Boolean tv) {
        isTv = tv;
    }

    public List<Integer> getEpsRuntime() {
        return epsRuntime;
    }

    public void setEpsRuntime(List<Integer> epsRuntime) {
        this.epsRuntime = epsRuntime;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getPopCount() {
        return popCount;
    }

    public void setPopCount(float popCount) {
        this.popCount = popCount;
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

    public long getIds() {
        return ids;
    }

    public void setIds(long ids) {
        this.ids = ids;
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

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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

    public int getEpsCount() {
        return epsCount;
    }

    public void setEpsCount(int epsCount) {
        this.epsCount = epsCount;
    }

    public int getSeasCount() {
        return seasCount;
    }

    public void setSeasCount(int seasCount) {
        this.seasCount = seasCount;
    }

    @TypeConverter
    public String getFirstDate(Context context) {
        String input1 = "yyyy-MM-dd";
        if (firstDate != null && !firstDate.isEmpty()) {
            try {
                SimpleDateFormat inputF = new SimpleDateFormat(input1, java.util.Locale.US);
                Date date = inputF.parse(firstDate);
                return DateFormat.getDateInstance().format(date);
            } catch (ParseException e) {
                Log.e(TAG, "error to parse: " + firstDate);
            }

        } else {
            firstDate = "No Data";
        }
        return firstDate;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    @TypeConverter
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

    public String getReVenues() {
        return reVenues;
    }

    public void setReVenues(String reVenues) {
        this.reVenues = reVenues;
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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public List<Networks> getNetStation() {
        return netStation;
    }

    public void setNetStation(List<Networks> netStation) {
        this.netStation = netStation;
    }

    public int getGbrPoster() {
        return gbrPoster;
    }

    public void setGbrPoster(int gbrPoster) {
        this.gbrPoster = gbrPoster;
    }

}
