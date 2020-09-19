package com.example.mymovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mymovies.R;
import com.example.mymovies.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating a layout from XML and returning the holder

        //get inflater
        //the parent is actually the RecyclerView

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //by using inflater.inflate() you create your View from your XML file.
        View movieView = inflater.inflate(R.layout.movie_card_view, parent, false);

        // Return a new holder instance
        MovieViewHolder viewHolder = new MovieViewHolder(movieView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie currMoive = this.movies.get(position);

        holder.mMovieTitle.setText(currMoive.getTitle());
        holder.mMovieDesc.setText(currMoive.getOverview());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + currMoive.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .into(holder.mPosterImg);

    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        private TextView mMovieTitle;
        private TextView  mMovieDesc;
        private ImageView mPosterImg;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            //1.1  initialize member variables in constructor
            mMovieTitle = itemView.findViewById(R.id.movie_title);
            mMovieDesc =  itemView.findViewById(R.id.movie_desc);
            mPosterImg =  itemView.findViewById(R.id.movie_poster);

        }


    }
}
