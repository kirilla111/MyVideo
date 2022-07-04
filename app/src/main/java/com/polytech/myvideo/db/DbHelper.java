package com.polytech.myvideo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyVideo.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "favourite";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FILE_NAME = "file_name";
    private static final String COLUMN_FILE_PATH = "file_path";

    private static final String HISTORY_TABLE_NAME = "history";
    private static final String HISTORY_COLUMN_ID = "_id";
    private static final String HISTORY_COLUMN_NAME = "file_name";
    private static final String HISTORY_COLUMN_PATH = "file_path";

    private Context context;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FILE_NAME + " TEXT, " +
                COLUMN_FILE_PATH + " TEXT); ";
        db.execSQL(query);

        query = "CREATE TABLE " + HISTORY_TABLE_NAME +
                " (" + HISTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HISTORY_COLUMN_NAME + " TEXT, " +
                HISTORY_COLUMN_PATH + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE_NAME);
        onCreate(db);
    }

    public void addFavourite(String fileName, String path) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (existByPath(path)) return;

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FILE_NAME, fileName);
        cv.put(COLUMN_FILE_PATH, path);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add Favourite", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Added to Favourite", Toast.LENGTH_LONG).show();
            Log.d(TAG, "addFavourite:" + existByPath(path));
        }
    }

    public boolean existByPath(String path) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_FILE_PATH + " = '" + path + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToNext())
                return true;
        }
        return false;
    }


    public ArrayList<FavouriteDto> readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            ArrayList<FavouriteDto> favourites = new ArrayList<>();
            while (cursor.moveToNext()) {
                favourites.add(new FavouriteDto(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
            return favourites;
        }
        return null;
    }

    public void removeFavourite(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id);
    }

    public ArrayList<FavouriteDto> readHistory() {
        String query = "SELECT * FROM " + HISTORY_TABLE_NAME + " ORDER BY "+ HISTORY_COLUMN_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            ArrayList<FavouriteDto> history = new ArrayList<>();
            while (cursor.moveToNext()) {
                history.add(new FavouriteDto(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
            return history;
        }
        return null;
    }

    public void addNewHistory(String fileName, String path) {
        SQLiteDatabase db = this.getWritableDatabase();

        removeByPath(db, path);

        ContentValues cv = new ContentValues();

        cv.put(HISTORY_COLUMN_NAME, fileName);
        cv.put(HISTORY_COLUMN_PATH, path);

        long result = db.insert(HISTORY_TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add History", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Added to History", Toast.LENGTH_LONG).show();
        }
    }

    private void removeByPath(SQLiteDatabase db, String path){
        db.execSQL("DELETE FROM " + HISTORY_TABLE_NAME + " WHERE " + HISTORY_COLUMN_PATH + " = '" + path + "'");
    }

    public void removeFromHistory(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + HISTORY_TABLE_NAME + " WHERE " + HISTORY_COLUMN_ID + " = " + id);
    }
}
