package com.cde.pcfsample.movie.model;

public class Rating {
    private int id;

    private int rating;

    private String ratedBy;

    public Rating() {

    }
    public Rating(int id, int rating, String ratedBy) {
        this.id = id;
        this.rating = rating;
        this.ratedBy = ratedBy;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rating=" + rating +
                ", ratedBy='" + ratedBy + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(String ratedBy) {
        this.ratedBy = ratedBy;
    }
}
