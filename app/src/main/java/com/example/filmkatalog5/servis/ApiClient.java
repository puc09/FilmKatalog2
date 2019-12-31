package com.example.filmkatalog5.servis;

import com.example.filmkatalog5.model.ApiResponse;
import com.example.filmkatalog5.model.Film;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiClient {

    @GET("discover/movie")
    Call<ApiResponse> getMovies
            (@Query("api_key") String apiKey, @Query("language") String lang);

    @GET("discover/movie")
    Call<ApiResponse> getNewMovies
            (@Query("api_key") String apiKey, @Query("language") String lang, @Query("primary_release_date.gte") String releaseFromDate, @Query("primary_release_date.lte") String releaseUntilDate);


    @GET("discover/tv")
    Call<ApiResponse> getSeries
            (@Query("api_key") String apiKey, @Query("language") String lang);

    @GET("movie/{movie_id}")
    Call<Film> getDetails
            (@Path("movie_id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String credits);

    @GET("tv/{tv_id}")
    Call<Film> getDetailTv
            (@Path("tv_id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String credits);

    @GET("search/movie")
    Call<ApiResponse> searchMovies
            (@Query("api_key") String apiKey, @Query("language") String lang, @Query("query") String movieName);

    @GET("search/tv")
    Call<ApiResponse> searchSeries
            (@Query("api_key") String apiKey, @Query("language") String lang, @Query("query") String tvName);
}
