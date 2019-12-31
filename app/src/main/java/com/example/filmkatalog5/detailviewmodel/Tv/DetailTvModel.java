package com.example.filmkatalog5.detailviewmodel.Tv;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filmkatalog5.data.repository.DetailFilmRepository;
import com.example.filmkatalog5.model.Film;

import retrofit2.Call;

public class DetailTvModel extends ViewModel {
    public static final String TAG = "detailTvModel";
    private MutableLiveData<Film> series = new MutableLiveData<>();
    private LiveData<Boolean> error = new MutableLiveData<>();
    private LiveData<Boolean> loading = new MutableLiveData<>();
    private Call<Film> apiCalls;
    private DetailFilmRepository repository;

    public DetailTvModel(Application application, int Ids) {
        repository = new DetailFilmRepository(application);
        series = repository.getTvDetail(Ids);
        loading = repository.isLoading();
        error = repository.getError();
    }


    public LiveData<Film> getSeries() {
        return series;
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<Film> getFavoritSeries(Integer tvIds) {
        return repository.getDetail(tvIds);

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (apiCalls != null) {
            apiCalls.cancel();
            apiCalls = null;
        }
    }
}
