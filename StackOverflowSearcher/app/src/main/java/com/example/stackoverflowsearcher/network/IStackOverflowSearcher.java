package com.example.stackoverflowsearcher.network;

import com.example.stackoverflowsearcher.models.StackoverflowResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface IStackOverflowSearcher {
    @GET("search")
    Call<StackoverflowResult> getDiscussionsBasedOnSearch(@Query("tagged") String tagged,
                                                          @Query("order") String order,
                                                          @Query("sort") String sort,
                                                          @Query("site") String site);

}
