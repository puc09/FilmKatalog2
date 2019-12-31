package com.example.filmkatalog5.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.filmkatalog5.data.dao.FavDao;
import com.example.filmkatalog5.model.Film;

@Database(entities = {Film.class},
        version = 1,
        exportSchema = false)
public abstract class DatabaseFavorit extends RoomDatabase {

    public static final String DATABASE_NAME = "FilmKatalog4.db";
    private static final Object LOCK = new Object();
    private static DatabaseFavorit INSTANCE;
    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static DatabaseFavorit getInstance(Context context) {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = buildDb(context);
            }
            return INSTANCE;

        }
    }

    private static DatabaseFavorit buildDb(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                DatabaseFavorit.class,
                DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(dbCallback)
                .build();
    }

    public abstract FavDao favoritDao();

}
