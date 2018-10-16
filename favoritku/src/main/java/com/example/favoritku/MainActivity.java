package com.example.favoritku;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.favoritku.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_category)
    RecyclerView rv_favorite;

    private Cursor list;
    private FavoritAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new FavoritAdapter(this);
        adapter.replaceAll(list);
        rv_favorite.setLayoutManager(new LinearLayoutManager(this));
        rv_favorite.setAdapter(adapter);


        new LoadDataAsync().execute();
    }

    @Override
    protected void onResume() {
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
            return getContentResolver().query(
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

            list = cursor;
            adapter.replaceAll(list);
            if (list.getCount() == 0){
                Toast.makeText(MainActivity.this,"Tidak ada data",Toast.LENGTH_SHORT).show();
            }


        }
    }
}
