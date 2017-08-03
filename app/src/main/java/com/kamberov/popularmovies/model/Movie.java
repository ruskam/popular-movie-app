package com.kamberov.popularmovies.model;

import java.io.Serializable;

/**
 * Created by ESR14 on 04/02/2017.
 */

public class Movie implements Serializable{

    private int id;
    private String title;
    private String image_thumbnail;
    private String overview;
    private double rating;
    private String date;

    public Movie() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_thumbnail() {
        return image_thumbnail;
    }

    public void setImage_thumbnail(String image_thumbnail) {
        this.image_thumbnail = image_thumbnail;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Movie{" + '\n' +
                "   title='" + title + '\n' +
                "   image_thumbnail='" + image_thumbnail + '\n' +
                "   overview='" + overview + '\n' +
                "   rating=" + rating + '\n' +
                "   date='" + date + '\n' +
                '}';
    }
}
