package com.example.filmkatalog5.data.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.filmkatalog5.model.Film;

import java.util.List;

@Dao
public interface FavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Film favoritFilm);

    @Transaction
    @Query("SELECT * FROM favorite WHERE id=:ids")
    LiveData<Film> getFavDetail(Integer ids);

    @Query("DELETE FROM favorite WHERE id=:ids")
    void delete(Integer ids);

    @Query("SELECT * FROM favorite")
    LiveData<List<Film>> getAllFav();

    @Query("SELECT * FROM favorite WHERE ismovie=1")
    LiveData<List<Film>> getIsMovie();

    @Query("SELECT * FROM favorite WHERE istv=1")
    LiveData<List<Film>> getIsTv();

    @Query("SELECT * FROM favorite WHERE ismovie=1")
    Cursor getAllMovie();

    @Query("SELECT * FROM favorite WHERE istv=1")
    Cursor getAllTv();

}
