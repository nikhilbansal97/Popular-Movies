package com.example.nikhil.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nikhil.popularmovies.Retrofit.ApiClient;
import com.example.nikhil.popularmovies.Retrofit.ApiInterface;
import com.example.nikhil.popularmovies.pojos.Response;
import com.example.nikhil.popularmovies.pojos.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    public static final String SORT_POPULARITY = "popularity.desc";
    public static final String SORT_RATING = "vote_average.desc";
    public static final String API_KEY = "e29ebd1566209e1d0a4279df87798d4f";
    private List<Results> movies;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        // Bind Views
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MovieAdapter(this,movies);
        recyclerView.setAdapter(adapter);

        // Retrofit Call
        getMovies(SORT_POPULARITY);
    }

    public void getMovies(String sort_by){
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Response> call = apiInterface.getMovies(sort_by,API_KEY);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.v(TAG , "onResponse()");
                Response response1 = response.body();
                movies = response1.getResults();
                for(int i=0;i<movies.size();i++)
                {
                    if(movies.get(i).getPoster_path() == null || movies.get(i).getPoster_path().contains("null"))
                        movies.remove(i);
                }
                //adapter = new MovieAdapter(getApplicationContext(),movies);
                //recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                for(int i=0;i<movies.size();i++)
                    Log.v(TAG , "https://image.tmdb.org/t/p/w500"+movies.get(i).getPoster_path());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.v(TAG , "onFailure()");
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popular_movies :
                getMovies(SORT_POPULARITY);
                break;
            case R.id.rated_movies :
                getMovies(SORT_RATING);
                break;
            default:
                throw new IllegalArgumentException("Wrong item selected");
        }
        return true;
    }
}
