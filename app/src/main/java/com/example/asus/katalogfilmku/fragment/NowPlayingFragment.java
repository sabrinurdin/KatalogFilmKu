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

import com.example.asus.katalogfilmku.Loader.MyAsyncTaskLoaderNowPlaying;
import com.example.asus.katalogfilmku.R;
import com.example.asus.katalogfilmku.adapter.NowPlayingAdapter;
import com.example.asus.katalogfilmku.model.FilmItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment  implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>> {
    @BindView(R.id.rv_category)
    RecyclerView listView;
    NowPlayingAdapter adapter;
    private View view;
    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        adapter = new NowPlayingAdapter(getActivity());
        adapter.notifyDataSetChanged();
        ButterKnife.bind(this,view);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(adapter);
        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(0,bundle, this);

        return view;
    }
    //Fungsi ini yang akan menjalankan proses myasynctaskloader
    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, Bundle args) {



        return new MyAsyncTaskLoaderNowPlaying(getActivity());
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

