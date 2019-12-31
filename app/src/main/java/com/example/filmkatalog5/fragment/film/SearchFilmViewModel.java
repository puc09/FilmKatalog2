package com.example.filmkatalog5.fragment.film;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filmkatalog5.BuildConfig;
import com.example.filmkatalog5.model.ApiResponse;
import com.example.filmkatalog5.model.Film;
import com.example.filmkatalog5.servis.ApiClient;
import com.example.filmkatalog5.servis.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Video.VideoColumns.LANGUAGE;
import static com.example.filmkatalog5.utility.Constant.BASE_URL;

public class SearchFilmViewModel extends ViewModel {
    private static String TAG = "SearchFilmViewModel";
    private MutableLiveData<ArrayList<Film>> movies = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private Call<ApiResponse> apiCall;

    public void getSearchFilm(String query) {
        loading.setValue(true);
        apiCall = RestClient.getClient(BASE_URL).create(ApiClient.class).searchMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY, LANGUAGE, query);
        apiCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                try {
                    ArrayList<Film> list = response.body().getFilms();
                    movies.postValue(list);
                    loading.setValue(false);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    loading.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.d(TAG, t.toString());
                error.setValue(true);
            }
        });
    }

    public LiveData<ArrayList<Film>> getSearchResult() {
        return movies;
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
