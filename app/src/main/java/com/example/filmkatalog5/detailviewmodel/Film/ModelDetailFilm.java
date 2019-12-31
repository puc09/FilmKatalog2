package com.example.filmkatalog5.detailviewmodel.Film;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filmkatalog5.data.repository.DetailFilmRepository;
import com.example.filmkatalog5.model.Film;

import retrofit2.Call;

public class ModelDetailFilm extends ViewModel {
    public static final String TAG = "filmDetailModel";
    private MutableLiveData<Film> movies = new MutableLiveData<>();
    private LiveData<Boolean> loading = new MutableLiveData<>();
    private LiveData<Boolean> error = new MutableLiveData<>();
    private Call<Film> apiCall;
    private DetailFilmRepository repository;

    public ModelDetailFilm(Application application, int id) {
        repository = new DetailFilmRepository(application);
        movies = repository.getFilmDetail(id);
        loading = repository.isLoading();
        error = repository.getError();

    }

    public LiveData<Film> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public LiveData<Film> getFavoritFilm(Integer ids) {
        return repository.getDetail(ids);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (apiCall != null) {
            apiCall.cancel();
            apiCall = null;
        }
    }


}

