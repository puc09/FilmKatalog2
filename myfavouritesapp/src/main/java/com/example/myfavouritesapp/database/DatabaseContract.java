package com.example.myfavouritesapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.filmkatalog5";
    public static final String SCHEME = "content";

    public static final class FaveDbColumns implements BaseColumns {


        public static final String TABLE_NAME = "favorite";


        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY).appendPath(TABLE_NAME).build();
    }

}
