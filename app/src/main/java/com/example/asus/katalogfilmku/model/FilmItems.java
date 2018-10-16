package com.example.asus.katalogfilmku.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.asus.katalogfilmku.db.DatabaseContract;


import org.json.JSONObject;

import static com.example.asus.katalogfilmku.db.DatabaseContract.getColumnInt;
import static com.example.asus.katalogfilmku.db.DatabaseContract.getColumnString;


public  class FilmItems implements Parcelable {
    private int id;
    private String poster;
    private String title;
    private String overview;
    private String release_date;
    private String popularity;
    private  String vote;
    public FilmItems(JSONObject object){
        try {
            int id = object.getInt("id");
            String Poster =object.getString("poster_path");
            String Title = object.getString("title");
            String Overview = object.getString("overview");
            String Release_date = object.getString("release_date");
            String Popularity = object.getString("popularity");
            String Vote = object.getString("vote_average");
            this.id = id;
            this.poster ="http://image.tmdb.org/t/p/w500/" + Poster;
            this.title = Title;
            this.overview = Overview;
            this.release_date = Release_date;
            this.popularity = Popularity;
            this.vote = Vote;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public FilmItems() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }
    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.popularity);
        dest.writeString(this.vote);
    }

    public FilmItems(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.NoteColumns.ID_MOVIE);
        this.poster = getColumnString(cursor, DatabaseContract.NoteColumns.POSTER);
        this.title = getColumnString(cursor, DatabaseContract.NoteColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.NoteColumns.OVERVIEW);
        this.release_date = getColumnString(cursor, DatabaseContract.NoteColumns.DATE);
        this.popularity = getColumnString(cursor, DatabaseContract.NoteColumns.POPULARITY);
        this.vote = getColumnString(cursor, DatabaseContract.NoteColumns.VOTE);
    }


    protected FilmItems(Parcel in) {
        this.id = in.readInt();
        this.poster = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.popularity = in.readString();
        this.vote = in.readString();
    }

    public static final Creator<FilmItems> CREATOR = new Creator<FilmItems>() {
        @Override
        public FilmItems createFromParcel(Parcel source) {
            return new FilmItems(source);
        }

        @Override
        public FilmItems[] newArray(int size) {
            return new FilmItems[size];
        }
    };
}
