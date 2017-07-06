package com.example.nikhil.popularmovies.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikhil on 6/7/17.
 */

public class ApiClient {

    private static Retrofit retrofit = null;
    public static final String BASE_URL = "https://api.themoviedb.org/";

    public static Retrofit getClient(){
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }

}
