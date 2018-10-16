package com.example.asus.katalogfilmku;

import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asus.katalogfilmku.Loader.MyAsyncTaskLoader;
import com.example.asus.katalogfilmku.adapter.FilmAdapter;
import com.example.asus.katalogfilmku.model.FilmItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CariActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>>, AdapterView.OnItemClickListener {
    Unbinder unbinder;
    @BindView(R.id.listView)
    ListView listView;

    FilmAdapter adapter;

    public static final String EXTRAS_FILMS = "EXTRAS_FILM";

SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari);
        unbinder = ButterKnife.bind(this);
        adapter = new FilmAdapter(this);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        Bundle bundle = new Bundle();
        String title = getIntent().getStringExtra(EXTRAS_FILMS);
        bundle.putString(EXTRAS_FILMS,title);
        getSupportLoaderManager().initLoader(0,bundle,this);

    }

    //Fungsi ini yang akan menjalankan proses myasynctaskloader
    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, Bundle args) {
        String kumpulanFilm = "a";
        if (args != null){
            kumpulanFilm = args.getString(EXTRAS_FILMS);
        }
        return new MyAsyncTaskLoader(this,kumpulanFilm);
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> data) {

        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setData(null);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        FilmItems mfilmItems = new FilmItems();
        mfilmItems.setId(adapter.getItem(position).getId());
        mfilmItems.setTitle(adapter.getItem(position).getTitle());
        mfilmItems.setOverview(adapter.getItem(position).getOverview());
        mfilmItems.setRelease_date(adapter.getItem(position).getRelease_date());
        mfilmItems.setPoster(adapter.getItem(position).getPoster());
        mfilmItems.setPopularity(adapter.getItem(position).getPopularity());
        mfilmItems.setVote(adapter.getItem(position).getVote());

        Intent DataIntent = new Intent(this, DetailActivity.class);
        DataIntent.putExtra(DetailActivity.EXTRA_FILM,mfilmItems);
        DataIntent.putExtra(DetailActivity.KATA,mfilmItems.getTitle());
        startActivity(DataIntent);
    }
}
