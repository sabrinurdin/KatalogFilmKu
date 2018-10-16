package com.example.asus.katalogfilmku.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.asus.katalogfilmku.db.DatabaseContract;
import com.example.asus.katalogfilmku.db.FavoritHelper;


import static com.example.asus.katalogfilmku.db.DatabaseContract.AUTHORITY;
import static com.example.asus.katalogfilmku.db.DatabaseContract.CONTENT_URI;


public class FavoritProvider extends ContentProvider {

    private static final int FAVORIT = 1;
    private static final int FAVORIT_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        // content://com.dicoding.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORIT, FAVORIT);

        // content://com.dicoding.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_FAVORIT + "/#",
                FAVORIT_ID);
    }

    private FavoritHelper noteHelper;

    @Override
    public boolean onCreate() {
        noteHelper = new FavoritHelper(getContext());
        noteHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORIT:
                cursor = noteHelper.queryProvider();
                break;
            case FAVORIT_ID:
                cursor = noteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added;

        switch (sUriMatcher.match(uri)) {
            case FAVORIT:
                added = noteHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FAVORIT_ID:
                updated = noteHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVORIT_ID:
                deleted = noteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

}