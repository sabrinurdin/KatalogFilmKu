package com.example.favoritku.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAVORIT = "favorit";

    public static final class NoteColumns implements BaseColumns {
        //Note description
        public static String ID_MOVIE = "id";
        public static String POSTER = "poster_path";
        //Note date
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        //Note description
        public static String DATE = "release_date";
        public static String POPULARITY = "popularity";
        public static String VOTE = "vote_average";
    }

    public static final String AUTHORITY = "com.example.asus.katalogfilmku";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORIT)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
