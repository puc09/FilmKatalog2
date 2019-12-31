package com.example.myfavouritesapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfavouritesapp.service.ApiClient;
import com.example.myfavouritesapp.BuildConfig;
import com.example.myfavouritesapp.service.RestClient;
import com.example.myfavouritesapp.model.Film;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myfavouritesapp.utils.Constant.BASE_URL;
import static com.example.myfavouritesapp.utils.Constant.CREDITS;

public class DetailViewModel extends ViewModel {
    public static final String TAG = DetailViewModel.class.getSimpleName();
    private MutableLiveData<Film> movies = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private Call<Film> apiCall;

    public void setMovies(int movieId) {
        isLoading.setValue(true);
        apiCall = RestClient.getClient(BASE_URL).create(ApiClient.class).getDetails(movieId, BuildConfig.THE_MOVIE_DATABASE_API_KEY, CREDITS);
        apiCall.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                try {
                    Film films = response.body();
                    movies.postValue(films);
                    isLoading.setValue(false);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.e(TAG, t.toString());
                isLoading.setValue(false);
            }
        });
    }

    public LiveData<Film> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

}
