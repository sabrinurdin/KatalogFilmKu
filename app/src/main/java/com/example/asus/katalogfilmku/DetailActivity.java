package com.example.asus.katalogfilmku;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.katalogfilmku.db.DatabaseContract;
import com.example.asus.katalogfilmku.db.FavoritHelper;
import com.example.asus.katalogfilmku.model.FilmItems;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.asus.katalogfilmku.db.DatabaseContract.CONTENT_URI;
import static com.example.asus.katalogfilmku.db.DatabaseContract.NoteColumns.DATE;
import static com.example.asus.katalogfilmku.db.DatabaseContract.NoteColumns.ID_MOVIE;
import static com.example.asus.katalogfilmku.db.DatabaseContract.NoteColumns.OVERVIEW;
import static com.example.asus.katalogfilmku.db.DatabaseContract.NoteColumns.POPULARITY;
import static com.example.asus.katalogfilmku.db.DatabaseContract.NoteColumns.POSTER;
import static com.example.asus.katalogfilmku.db.DatabaseContract.NoteColumns.TITLE;
import static com.example.asus.katalogfilmku.db.DatabaseContract.NoteColumns.VOTE;


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
    @BindView(R.id.img_item_photo)
    ImageView image2;
    @BindView(R.id.iv_fav)
    ImageView favo;
    @BindView(R.id.button_favorit)
    Button btnFavorit;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static final String KATA = "kata";

    private FavoritHelper favoritHelper;
    private Boolean Favorite = false;
    FilmItems film;
String id,title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            collapsing_toolbar.setTitle(getIntent().getStringExtra(KATA));
        collapsing_toolbar.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.button));
        collapsing_toolbar.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.button));


        film = getIntent().getParcelableExtra(EXTRA_FILM);
        UpDataUI(film);
        id = String.valueOf(film.getId());

        favoritHelper = new FavoritHelper(this);
        favoritHelper.open();

        Uri uri =Uri.parse(CONTENT_URI + "/" + film.getId());
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()) Favorite = true;
                cursor.close();

            }

            favoriteSet();
            setButton();
        }

        btnFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (Favorite)
                        HapusFavorit();
                    else
                        SimpanFavorit();

                    Favorite = !Favorite;
                    favoriteSet();
                }
            private void SimpanFavorit () {
                ContentValues values = new ContentValues();
                values.put(ID_MOVIE,film.getId());
                values.put(POSTER,film.getPoster());
                values.put(TITLE, film.getTitle());
                values.put(OVERVIEW, film.getOverview());
                values.put(DATE, film.getRelease_date());
                values.put(POPULARITY, film.getPopularity());
                values.put(VOTE, film.getVote());

                getContentResolver().insert(DatabaseContract.CONTENT_URI, values);
                btnFavorit.setText(R.string.hapus);
                Toast.makeText(getApplication(),R.string.toastfavotit,Toast.LENGTH_SHORT).show();
            }

            private void HapusFavorit() {
                if (getContentResolver() != null) {
                    getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + film.getId()), null, null);
                }
                btnFavorit.setText(R.string.favorit);
                Toast.makeText(getApplication(), R.string.toasthapus, Toast.LENGTH_SHORT).show();

            }

        });

    }
    private void UpDataUI(FilmItems films) {
        final String title = films.getTitle();
        Title.setText(title);
        final String date = films.getRelease_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateformat = dateFormat.parse(date);
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

        Glide.with(this)
                .asBitmap()
                .load(films.getPoster())
                .into(image2);

    }

    private void setButton() {
        if(Favorite) btnFavorit.setText(R.string.hapus);
        else btnFavorit.setText(R.string.favorit);

    }

    private void favoriteSet() {
        if (Favorite) favo.setImageResource(R.drawable.ic_favorite_black_24dp);
        else favo.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (favoritHelper != null) {
            favoritHelper.close();
        }

    }
}







