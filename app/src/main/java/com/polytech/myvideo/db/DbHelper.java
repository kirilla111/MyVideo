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
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "favourite";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FILE_NAME = "file_name";
    private static final String COLUMN_FILE_PATH = "file_path";

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
                COLUMN_FILE_PATH + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addFavourite(String fileName, String path){
        SQLiteDatabase db = this.getWritableDatabase();

        if (existByPath(path)) return;

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FILE_NAME, fileName);
        cv.put(COLUMN_FILE_PATH, path);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add Favourite", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Added to Favourite", Toast.LENGTH_LONG).show();
            Log.d(TAG, "addFavourite:" + existByPath(path));
        }
    }

    public boolean existByPath(String path){
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_FILE_PATH + " = '" + path+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
            if (cursor.moveToNext())
                return true;
        }
        return false;
    }


    public ArrayList<FavouriteDto> readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
            ArrayList<FavouriteDto> favourites = new ArrayList<>();
            while (cursor.moveToNext()){
                favourites.add(new FavouriteDto(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
            return favourites;
        }
        return null;
    }
}
