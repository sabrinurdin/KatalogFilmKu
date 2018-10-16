package com.example.asus.katalogfilmku.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.katalogfilmku.Loader.MyAsyncTaskLoaderUpcoming;
import com.example.asus.katalogfilmku.R;
import com.example.asus.katalogfilmku.adapter.UpcomingAdapter;
import com.example.asus.katalogfilmku.model.FilmItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment  implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>>{
    @BindView(R.id.rv_category)
    RecyclerView listView;
    UpcomingAdapter adapter;
    private View view;
    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this,view);
        adapter = new UpcomingAdapter(getActivity());
        adapter.notifyDataSetChanged();
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(adapter);
        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(0,bundle,this);

        return view;
    }
    //Fungsi ini yang akan menjalankan proses myasynctaskloader
    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, Bundle args) {



        return new MyAsyncTaskLoaderUpcoming(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> data) {

        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setData(null);

    }



}

