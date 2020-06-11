package com.sab.wordquiz.requests;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordServiceGenerator {
    private static OkHttpClient client = new OkHttpClient.Builder()
            // establish connection to server
            .connectTimeout(10, TimeUnit.SECONDS)
            // time between each byte read from the server
            .readTimeout(2, TimeUnit.SECONDS)
            // time between each byte sent to server
            .writeTimeout(2, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl("https://random-word-api.herokuapp.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RandomWordApi randomWordApi = retrofit.create(RandomWordApi.class);

    public static RandomWordApi getRandomWordApi() {
        return randomWordApi;
    }

}
