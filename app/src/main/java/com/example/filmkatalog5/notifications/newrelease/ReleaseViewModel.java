package com.example.filmkatalog5.notifications.newrelease;

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

public class ReleaseViewModel extends ViewModel {

    private static final String TAG = "ReleaseViewModel";
    private MutableLiveData<ArrayList<Film>> movies = new MutableLiveData<>();
    private Call<ApiResponse> apiCall;

    public void setListNew(String date) {
        apiCall = RestClient.getClient(BASE_URL).create(ApiClient.class).getNewMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY, LANGUAGE, date, date);
        apiCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                try {
                    ArrayList<Film> list = response.body().getFilms();
                    movies.postValue(list);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<Film>> getNewRelease() {
        return movies;
    }
}
