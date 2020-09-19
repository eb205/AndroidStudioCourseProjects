package com.example.mymovies;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.adapter.MovieAdapter;
import com.example.mymovies.models.Movie;
import com.example.mymovies.models.MoviePageResult;
import com.example.mymovies.network.GetMovieDataService;
import com.example.mymovies.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final static String API_KEY = "16b76e1053d0e2767581021fda1a09e1";
    private GetMovieDataService movieDataService;
    private List<Movie> movies;
    private RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.rvMovies=findViewById(R.id.recyclerView);
        this.movieDataService =
                RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);

        this.movieDataService.getTopRatedMovies(1, API_KEY).enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
                movies = response.body().getResults();

                for(Movie currMovie : movies) {
                    Log.i("Movies:", currMovie.getTitle());
                }

                MovieAdapter adapter= new MovieAdapter(movies);

                // Attach the adapter to the recyclerview to populate items
                rvMovies.setAdapter(adapter);

                // Set layout manager to position the items
                rvMovies.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed getting movie list", Toast.LENGTH_LONG);
            }
        });
    }


}