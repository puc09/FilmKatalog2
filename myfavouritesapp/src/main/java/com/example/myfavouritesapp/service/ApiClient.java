package com.example.myfavouritesapp.service;

import com.example.myfavouritesapp.model.Film;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiClient {

    @GET("movie/{movie_id}")
    Call<Film> getDetails
            (@Path("movie_id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String credits);

}
