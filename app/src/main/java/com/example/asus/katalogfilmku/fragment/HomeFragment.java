package com.example.asus.katalogfilmku.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.asus.katalogfilmku.DetailActivity;
import com.example.asus.katalogfilmku.Loader.MyAsyncTaskLoader;
import com.example.asus.katalogfilmku.R;
import com.example.asus.katalogfilmku.adapter.FilmAdapter;
import com.example.asus.katalogfilmku.model.FilmItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>>, AdapterView.OnItemClickListener {
    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.edit_film)
    EditText editFilm;

    @BindView(R.id.btn_film)
    Button buttonCari;

    FilmAdapter adapter;

    public static final String EXTRAS_FILMS = "EXTRAS_FILM";
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        adapter = new FilmAdapter(getActivity());
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);



        buttonCari.setOnClickListener(myListener);

        String film = editFilm.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILMS,film);

        getLoaderManager().initLoader(0, bundle, this);




        return view;
    }

    //Fungsi ini yang akan menjalankan proses myasynctaskloader
    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, Bundle args) {

        String kumpulanFilm = "";
        if (args != null){
            kumpulanFilm = args.getString(EXTRAS_FILMS);
        }

        return new MyAsyncTaskLoader(getActivity(),kumpulanFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> data) {

        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setData(null);

    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String film = editFilm.getText().toString();

            if (TextUtils.isEmpty(film)) return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_FILMS, film);
            getLoaderManager().restartLoader(0, bundle, HomeFragment.this);
        }
    };

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

        Intent DataIntent = new Intent(getActivity(), DetailActivity.class);
        DataIntent.putExtra(DetailActivity.EXTRA_FILM,mfilmItems);
        DataIntent.putExtra(DetailActivity.KATA,adapter.getItem(position).getTitle());
        startActivity(DataIntent);
    }
}
