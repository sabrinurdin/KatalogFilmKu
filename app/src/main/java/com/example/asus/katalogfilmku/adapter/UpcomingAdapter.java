package com.example.asus.katalogfilmku.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.katalogfilmku.utils.CustomOnItemClickListener;
import com.example.asus.katalogfilmku.DetailActivity;
import com.example.asus.katalogfilmku.R;
import com.example.asus.katalogfilmku.model.FilmItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    private List<FilmItems> mData;
    private Context context;

    public UpcomingAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<FilmItems> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final FilmItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;

        return mData.size();

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
        final FilmItems mFilm = mData.get(position);
        holder.textViewNamaFilm.setText(mFilm.getTitle());
        holder.textViewOverview.setText(mFilm.getOverview());
        String formatDate = mFilm.getRelease_date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(formatDate);
            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy ");
            String date_of_release = new_date_format.format(date);
            holder.textViewRelease.setText(date_of_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .asBitmap()
                .load(mFilm.getPoster())
                .into(holder.imgPoster);

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                FilmItems mfilmItems = new FilmItems();
                mfilmItems.setTitle(mData.get(position).getTitle());
                mfilmItems.setId(mData.get(position).getId());
                mfilmItems.setOverview(mData.get(position).getOverview());
                mfilmItems.setRelease_date(mData.get(position).getRelease_date());
                mfilmItems.setPoster(mData.get(position).getPoster());
                mfilmItems.setPopularity(mData.get(position).getPopularity());
                mfilmItems.setVote(mData.get(position).getVote());

                Intent DataIntent = new Intent(context, DetailActivity.class);
                DataIntent.putExtra(DetailActivity.EXTRA_FILM, mfilmItems);
                DataIntent.putExtra(DetailActivity.KATA,mData.get(position).getTitle());
                context.startActivity(DataIntent);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context,R.string.share,Toast.LENGTH_SHORT).show();
            }
        }));


    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamaFilm;
        TextView textViewOverview;
        TextView textViewRelease;
        ImageView imgPoster;
        Button btnDetail, btnShare;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewNamaFilm = (TextView) itemView.findViewById(R.id.textTitle);
            imgPoster = (ImageView) itemView.findViewById(R.id.img_item_photo);
            textViewOverview = (TextView) itemView.findViewById(R.id.textoverview);
            textViewRelease = (TextView) itemView.findViewById(R.id.textDate);
            btnDetail = (Button) itemView.findViewById(R.id.btn_set_favorite);
            btnShare = (Button) itemView.findViewById(R.id.btn_set_share);
        }

    }
}
