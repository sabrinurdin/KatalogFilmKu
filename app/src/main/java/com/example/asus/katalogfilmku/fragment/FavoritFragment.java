package com.example.asus.katalogfilmku.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.katalogfilmku.R;
import com.example.asus.katalogfilmku.adapter.FavoritAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.asus.katalogfilmku.db.DatabaseContract.CONTENT_URI;



/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritFragment extends Fragment {
    private Context context;
    private Unbinder unbinder;

    @BindView(R.id.rv_category)
    RecyclerView rv_favorite;

    private Cursor listFavorit;
    private FavoritAdapter adapter;

    public FavoritFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorit, container, false);
        context = view.getContext();

        unbinder = ButterKnife.bind(this, view);

        adapter = new FavoritAdapter(context);
        rv_favorite.setLayoutManager(new LinearLayoutManager(context));
        rv_favorite.setAdapter(adapter);
        new LoadDataAsync().execute();

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public void onResume() {
        super.onResume();
        new LoadDataAsync().execute();

    }

    private class LoadDataAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {

            return context.getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            listFavorit = cursor;
            adapter.replaceAll(listFavorit);

            if (listFavorit.getCount() == 0){
            Snackbar.make(rv_favorite,"Tidak ada favorit saat ini",Snackbar.LENGTH_SHORT).show();
            }
        }
    }

}
