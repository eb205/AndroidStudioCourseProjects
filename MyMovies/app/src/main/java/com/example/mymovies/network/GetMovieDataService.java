package com.example.mymovies.network;

import com.example.mymovies.models.MoviePageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMovieDataService {
    //The relative URL of the resource and parameters is specified in the annotation
    @GET("movie/top_rated")
    Call<MoviePageResult> getTopRatedMovies(
            @Query("page") int page,
            @Query("api_key") String userkey);

}
