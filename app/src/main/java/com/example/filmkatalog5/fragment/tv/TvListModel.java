package com.example.filmkatalog5.fragment.tv;

import android.util.Log;

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
import static com.example.filmkatalog5.utility.Constant.LANGUANGE;

public class TvListModel extends ViewModel {
    private static final String TAG = "tvListModel";
    private MutableLiveData<ArrayList<Film>> series = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private Call<ApiResponse> apiCall;

    public void getSeries() {
        loading.setValue(true);
        apiCall = RestClient.getClient(BASE_URL).create(ApiClient.class).getSeries(BuildConfig.THE_MOVIE_DATABASE_API_KEY, LANGUANGE);
        apiCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                try {
                    ArrayList<Film> listSeries = response.body().getFilms();
                    series.postValue(listSeries);
                    loading.setValue(false);
                } catch (Exception e) {
                    Log.e(TAG, toString());
                    loading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                error.setValue(true);
                loading.setValue(false);
            }
        });
    }

    public void getSearchTv(String query) {
        loading.setValue(true);
        apiCall = RestClient.getClient(BASE_URL).create(ApiClient.class).searchSeries(BuildConfig.THE_MOVIE_DATABASE_API_KEY, LANGUAGE, query);
        apiCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                try {
                    ArrayList<Film> tvList = response.body().getFilms();
                    series.postValue(tvList);
                    loading.setValue(false);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<Film>> getTvSeries() {
        return series;
    }

    public LiveData<ArrayList<Film>> getSearchResults() {
        return series;
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
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
