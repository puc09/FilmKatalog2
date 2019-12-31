package com.example.filmkatalog5.fragment.favorit.film;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmkatalog5.data.repository.DetailFilmRepository;
import com.example.filmkatalog5.model.Film;

import java.util.List;

public class FilmFavoritVM extends AndroidViewModel {
    private LiveData<List<Film>> listFavorit;
    private LiveData<Boolean> loading = new MutableLiveData<>();

    private DetailFilmRepository repository;

    public FilmFavoritVM(@NonNull Application application) {
        super(application);
        repository = new DetailFilmRepository(application);
        listFavorit = repository.getIsMovie();
        loading = repository.isLoading();
    }


    public LiveData<List<Film>> getListFavorit() {
        return listFavorit;
    }

    public LiveData<List<Film>> getIsMovie() {
        return listFavorit;
    }

    public void insert(Film favoritFilm) {
        repository.insert(favoritFilm);
    }

    public void remove(Film favoritFilm) {
        repository.delete(favoritFilm);
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }


}
