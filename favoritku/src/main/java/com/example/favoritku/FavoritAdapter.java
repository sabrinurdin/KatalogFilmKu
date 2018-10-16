package com.example.favoritku;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.favoritku.db.DatabaseContract.CONTENT_URI;


public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.ViewHolder> {
    private Cursor listFavorit;
    private Activity activity;


    public FavoritAdapter(Activity items) {

        this.activity = items;
    }

    public void replaceAll(Cursor list) {
        this.listFavorit = list;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if ( listFavorit == null) return 0;

        return listFavorit.getCount();

    }

    private Favorit getItem(int position){
        if (!listFavorit.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorit(listFavorit);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_nowplaying, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Favorit mFavorit = getItem(position);
        holder.textViewNamaFilm.setText(mFavorit.getTitle());
        holder.textViewOverview.setText(mFavorit.getOverview());
        String formatDate = mFavorit.getRelease_date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(formatDate);
            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy ");
            String date_of_release = new_date_format.format(date);
            holder.textViewRelease.setText(date_of_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(activity)
                .asBitmap()
                .load(mFavorit.getPoster())
                .into(holder.imgPoster);

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Favorit mfilmItems = new Favorit();
                mfilmItems.setTitle(mFavorit.getTitle());
                mfilmItems.setOverview(mFavorit.getOverview());
                mfilmItems.setRelease_date(mFavorit.getRelease_date());
                mfilmItems.setPoster(mFavorit.getPoster());
                mfilmItems.setPopularity(mFavorit.getPopularity());
                mfilmItems.setVote(mFavorit.getVote());
                Intent DataIntent = new Intent(activity, DetailActivity.class);
                DataIntent.putExtra(DetailActivity.EXTRA_FILM,mfilmItems);
                Uri uri = Uri.parse(CONTENT_URI+"/"+mFavorit.getId());
                DataIntent.setData(uri);
                activity.startActivity(DataIntent);
            }
        }));


    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textTitle)
        TextView textViewNamaFilm;
        @BindView(R.id.textoverview)
        TextView textViewOverview;
        @BindView(R.id.textDate)
        TextView textViewRelease;
        @BindView(R.id.img_item_photo)
        ImageView imgPoster;
        @BindView(R.id.btn_set_favorite)
        Button btnDetail;
        @BindView(R.id.btn_set_share)
        Button btnShare;
        @BindView(R.id.card_view)
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
