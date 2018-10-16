package com.example.asus.katalogfilmku.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.katalogfilmku.R;
import com.example.asus.katalogfilmku.model.FilmItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FilmAdapter extends BaseAdapter {

    private ArrayList<FilmItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public FilmAdapter(FragmentActivity context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<FilmItems> items){
        mData = items;
        notifyDataSetChanged();
    }
    public void addItem(final FilmItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    public void clearData(){
        mData.clear();
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }
    @Override
    public FilmItems getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_card_view_film, null);
            holder.textViewNamaFilm= (TextView)convertView.findViewById(R.id.textTitle);
            holder.textViewOverview = (TextView)convertView.findViewById(R.id.textoverview);
            holder.textViewRelease = (TextView)convertView.findViewById(R.id.textDate);
            holder.imgPoster = (ImageView)convertView.findViewById(R.id.img_item_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewNamaFilm.setText(mData.get(position).getTitle());
        holder.textViewOverview.setText(mData.get(position).getOverview());
        String formatDate = mData.get(position).getRelease_date();
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
                .load(mData.get(position).getPoster())
                .into(holder.imgPoster);
        return convertView;

    }
    private static class ViewHolder {
        TextView textViewNamaFilm;
        TextView textViewOverview;
        TextView textViewRelease;
        ImageView imgPoster;
    }
}

