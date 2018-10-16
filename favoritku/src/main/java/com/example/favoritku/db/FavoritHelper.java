package com.example.favoritku.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.favoritku.Favorit;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;


public class FavoritHelper {
    private static String DATABASE_TABLE = DatabaseContract.TABLE_FAVORIT;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase sqliteDatabase;

    public FavoritHelper(Context context){
        this.context = context;
    }

    public FavoritHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        sqliteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        sqliteDatabase.close();
    }

    public Cursor queryProvider() {
        return sqliteDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.NoteColumns._ID+ " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return sqliteDatabase.query(DATABASE_TABLE, null
                , DatabaseContract.NoteColumns.ID_MOVIE + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertProvider(ContentValues values) {
        return sqliteDatabase.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return sqliteDatabase.update(DATABASE_TABLE, values,
                DatabaseContract.NoteColumns.ID_MOVIE + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return sqliteDatabase.delete(DATABASE_TABLE,
                DatabaseContract.NoteColumns.ID_MOVIE + " = ?", new String[]{id});
    }

}
