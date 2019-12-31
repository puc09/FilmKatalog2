package com.example.filmkatalog5.data.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.filmkatalog5";
    public static final String SCHEME = "content";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static float getColumnFloat(Cursor cursor, String columnName) {
        return cursor.getFloat(cursor.getColumnIndex(columnName));
    }

    public static final class FaveDbColumns implements BaseColumns {

        public static final String DATABASE_NAME = "FilmKatalog4.db";
        public static final String TABLE_NAME = "favorite";
        public static final String KEY_ID = "id";
        public static final String FILM_TITLE = "title";
        public static final String VOTE = "vote_count";
        public static final String SINOPSIS = "overview";
        public static final String TV_TITLE = "name";
        public static final String FIRST_DATE = "first_air_date";
        public static final String RELEASE_DATE = "release_date";
        public static final String POSTER = "poster_path";
        public static final String RATING = "vote_average";
        public static final String LANGUAGE = "bahasa";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY).appendPath(TABLE_NAME).build();
    }
}
