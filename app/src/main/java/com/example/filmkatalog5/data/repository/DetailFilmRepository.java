package com.example.filmkatalog5.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmkatalog5.BuildConfig;
import com.example.filmkatalog5.data.dao.FavDao;
import com.example.filmkatalog5.data.database.DatabaseFavorit;
import com.example.filmkatalog5.model.Film;
import com.example.filmkatalog5.servis.ApiClient;
import com.example.filmkatalog5.servis.RestClient;
import com.example.filmkatalog5.utility.RoomExecutor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.filmkatalog5.utility.Constant.BASE_URL;
import static com.example.filmkatalog5.utility.Constant.CREDITS;

public class DetailFilmRepository {
    private static final String TAG = "detailFilmRepo";
    private RoomExecutor executor;
    private LiveData<List<Film>> dataFavorit;
    private Call<Film> apiCall;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private FavDao dao;

    public DetailFilmRepository(Application application) {
        DatabaseFavorit data = DatabaseFavorit.getInstance(application);
        dao = data.favoritDao();
        executor = RoomExecutor.getInstance();
    }

    public MutableLiveData<Film> getFilmDetail(final int movieIds) {
        final MutableLiveData<Film> movies = new MutableLiveData<>();
        loading.setValue(true);
        apiCall = RestClient.getClient(BASE_URL).create(ApiClient.class).getDetails(movieIds, BuildConfig.THE_MOVIE_DATABASE_API_KEY, CREDITS);
        apiCall.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                try {
                    Film films = response.body();
                    movies.postValue(films);

                    loading.setValue(false);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    loading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.e(TAG, t.toString());
                error.setValue(true);
                loading.setValue(false);
            }
        });
        return movies;
    }

    public MutableLiveData<Film> getTvDetail(int tvIds) {
        final MutableLiveData<Film> series = new MutableLiveData<>();
        loading.setValue(true);
        apiCall = RestClient.getClient(BASE_URL).create(ApiClient.class).getDetailTv(tvIds, BuildConfig.THE_MOVIE_DATABASE_API_KEY, CREDITS);
        apiCall.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                try {
                    Film seri = response.body();
                    series.postValue(seri);
                    loading.setValue(false);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    loading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.e(TAG, t.toString());
                error.setValue(true);
                loading.setValue(false);
            }
        });
        return series;
    }

    public LiveData<List<Film>> getDataFavorit() {
        dataFavorit = dao.getAllFav();
        return dataFavorit;
    }

    public LiveData<List<Film>> getIsMovie() {
        dataFavorit = dao.getIsMovie();
        return dataFavorit;
    }

    public LiveData<List<Film>> getIsTv() {
        dataFavorit = dao.getIsTv();
        return dataFavorit;
    }

    public LiveData<Film> getDetail(Integer ids) {
        return dao.getFavDetail(ids);
    }

    public void insert(final Film favoritFilm) {
        executor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(favoritFilm);
            }
        });
    }

    public void delete(final Film favoritFilm) {
        executor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(favoritFilm.getTheIds());
            }
        });

    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> isLoading() {
        return loading;
    }


}
