package com.example.nikhil.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.nikhil.popularmovies.pojos.Results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 6/7/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private static final String TAG = "MovieAdapter";
    private Context mContext;
    private List<Results> mList = new ArrayList<>();

    public MovieAdapter(Context context , List<Results> results) {

        mList = results;
        mContext = context;
        if (mList != null)
            Log.v(TAG,String.valueOf(mList.size()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Results movie = mList.get(position);
        holder.progress.setVisibility(View.VISIBLE);
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path()).into(holder.movie_poster);
        holder.progress.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        else
            return  0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView movie_poster;
        public ProgressBar progress;
        public ViewHolder(View itemView) {
            super(itemView);
            movie_poster = (ImageView) itemView.findViewById(R.id.imageView_listItem);
            progress = (ProgressBar) itemView.findViewById(R.id.progressBar_listItem);
        }
    }

}
