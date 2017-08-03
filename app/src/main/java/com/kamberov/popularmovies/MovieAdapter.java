package com.kamberov.popularmovies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamberov.popularmovies.model.Movie;
import com.kamberov.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by ESR14 on 08/02/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private static final String SIZE_TYPE = "w185";

    //private Context mContext;

    private Movie[] mMovieData;

    private final MovieAdapterOnClickHandler mMovieAdapterOnClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    /*
    public MovieAdapter(Context context){
        mContext = context;
    }
    */

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        //mContext = context;
        mMovieAdapterOnClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        private final TextView mMovieTextView;
        private final ImageView mImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);

            mMovieTextView = (TextView) view.findViewById(R.id.tv_movie_data);
            mImageView = (ImageView) view.findViewById(R.id.iv_poster_image_main);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieData[adapterPosition];
            mMovieAdapterOnClickHandler.onClick(movie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movie = mMovieData[position];
        //movieAdapterViewHolder.mMovieTextView.setText(movie.getTitle());
        //String tempImageUrl = "http://image.tmdb.org/t/p/w185/WLQN5aiQG8wc9SeKwixW7pAR8K.jpg";
        //String tempImageUrlRaw = movie.getImage_thumbnail();

        URL imageUrl = NetworkUtils.buildImageUrl(SIZE_TYPE, movie.getImage_thumbnail());

        //movie.setImage_thumbnail(tempImageUrl);
        //movieAdapterViewHolder.mImageView.setImageBitmap(movie.getImage_thumbnail());

        Picasso.with(movieAdapterViewHolder.mImageView.getContext()).load(imageUrl.toString()).into(movieAdapterViewHolder.mImageView);

    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.length;
    }

    public void setWeatherData(Movie[] movieData){
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
