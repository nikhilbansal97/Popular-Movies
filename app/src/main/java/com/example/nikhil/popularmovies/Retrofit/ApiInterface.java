package com.example.nikhil.popularmovies.Retrofit;

import com.example.nikhil.popularmovies.pojos.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nikhil on 6/7/17.
 */

public interface ApiInterface {

    @GET("3/discover/movie")
    Call<Response> getMovies(@Query("sort_by") String sort,@Query("api_key") String key);

}
