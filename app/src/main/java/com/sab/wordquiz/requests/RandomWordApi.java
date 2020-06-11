package com.sab.wordquiz.requests;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomWordApi {
    @GET("word")
    Call<JsonArray> oneRandomWord(@Query("number") int wordAmount);
}
