package com.example.filmkatalog5.fragment.favorit.tv;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.filmkatalog5.data.repository.DetailFilmRepository;
import com.example.filmkatalog5.model.Film;

import java.util.List;

public class FavoritTvViewModel extends AndroidViewModel {
    private DetailFilmRepository repository;
    private LiveData<List<Film>> tvListFav;

    public FavoritTvViewModel(@NonNull Application application) {
        super(application);
        repository = new DetailFilmRepository(application);
        tvListFav = repository.getIsTv();
    }

    public LiveData<List<Film>> getTvListFav() {
        return tvListFav;
    }

    public void insert(Film seriFavorit) {
        repository.insert(seriFavorit);
    }

    public void remove(Film seriFavorit) {
        repository.delete(seriFavorit);
    }
}
