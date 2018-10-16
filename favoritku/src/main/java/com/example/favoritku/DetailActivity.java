package com.example.favoritku;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.favoritku.db.DatabaseContract;
import com.example.favoritku.db.FavoritHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.favoritku.db.DatabaseContract.NoteColumns.DATE;
import static com.example.favoritku.db.DatabaseContract.NoteColumns.OVERVIEW;
import static com.example.favoritku.db.DatabaseContract.NoteColumns.POPULARITY;
import static com.example.favoritku.db.DatabaseContract.NoteColumns.POSTER;
import static com.example.favoritku.db.DatabaseContract.NoteColumns.TITLE;
import static com.example.favoritku.db.DatabaseContract.NoteColumns.VOTE;

public class DetailActivity extends AppCompatActivity {
    public static String EXTRA_FILM = "extra_film";

    @BindView(R.id.tv_title)
    TextView Title;
    @BindView(R.id.tv_date)
    TextView Date;
    @BindView(R.id.tv_overview)
    TextView Overview;
    @BindView(R.id.tv_popularity)
    TextView Popularity;
    @BindView(R.id.tv_vote)
    TextView Vote;
    @BindView(R.id.image)
    ImageView image;


    private Boolean isFavorite = false;
    private FavoritHelper favoritHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        final Favorit films = getIntent().getParcelableExtra(EXTRA_FILM);
        final String title = films.getTitle();
        Title.setText(title);
        final String date = films.getRelease_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date dateformat = dateFormat.parse(date);
            SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String date_release = newDateFormat.format(dateformat);
            Date.setText(date_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String overview = films.getOverview();
        Overview.setText(overview);
        final String pupularity = films.getPopularity();
        Popularity.setText(pupularity);
        final String vote = films.getVote();
        Vote.setText(vote);

        Glide.with(this)
                .asBitmap()
                .load(films.getPoster())
                .into(image);
        favoritHelper = new FavoritHelper(this);
        favoritHelper.open();

    }

}








