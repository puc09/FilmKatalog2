package com.example.filmkatalog5.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.filmkatalog5.data.dao.FavDao;
import com.example.filmkatalog5.data.database.DatabaseFavorit;

import java.util.Objects;

import static com.example.filmkatalog5.data.database.DatabaseContract.AUTHORITY;
import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.TABLE_NAME;
import static com.example.filmkatalog5.data.database.DatabaseContract.SCHEME;

public class FavoritFilmProvider extends ContentProvider {

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    private static final int FILM = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FILM);
    }

    DatabaseFavorit favoritDb;
    FavDao favoritDao;

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        favoritDb = DatabaseFavorit.getInstance(getContext());
        favoritDao = favoritDb.favoritDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        if (sUriMatcher.match(uri) == FILM) {
            cursor = favoritDao.getAllMovie();
            if (cursor != null) {
                cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
                return cursor;
            }
        }
        throw new IllegalArgumentException("Unknown Uri: " + uri);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (sUriMatcher.match(uri)) {
            case FILM:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;

            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        return null;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[]
            selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String
            selection,
                      @Nullable String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return 0;
    }
}
