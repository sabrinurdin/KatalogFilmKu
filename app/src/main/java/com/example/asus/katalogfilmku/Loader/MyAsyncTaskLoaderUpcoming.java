package com.example.asus.katalogfilmku.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.asus.katalogfilmku.BuildConfig;
import com.example.asus.katalogfilmku.model.FilmItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoaderUpcoming extends AsyncTaskLoader<ArrayList<FilmItems>> {
    private ArrayList<FilmItems> mData;
    private boolean mHasResult = false;


    public MyAsyncTaskLoaderUpcoming(final Context context) {
        super(context);
        onContentChanged();
        ;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<FilmItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }



    @Override
    public ArrayList<FilmItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<FilmItems> filmItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + BuildConfig.API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject weather = results.getJSONObject(i);
                        FilmItems filmItems = new FilmItems(weather);
                        filmItemses.add(filmItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
            }
        });

        return filmItemses;
    }

    protected void onReleaseResources(ArrayList<FilmItems> data) {
        //nothing to do.
    }

}