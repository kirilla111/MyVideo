package com.polytech.myvideo.db.scripts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.polytech.myvideo.db.dto.FavouriteDto;

import java.util.ArrayList;

public class FavouriteTableCreationScript implements Script {
    private static final String TABLE_NAME = "favourite";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FILE_NAME = "file_name";
    private static final String COLUMN_FILE_PATH = "file_path";

    @Override
    public void create(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FILE_NAME + " TEXT, " +
                COLUMN_FILE_PATH + " TEXT); ";
        db.execSQL(query);
    }

    @Override
    public void update(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void addToFavourite(SQLiteDatabase db, String fileName, String path, Context context) {

        if (existByPath(db, path)) return;

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FILE_NAME, fileName);
        cv.put(COLUMN_FILE_PATH, path);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add Favourite", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Added to Favourite", Toast.LENGTH_LONG).show();
        }
    }

    public boolean existByPath(SQLiteDatabase db, String path) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_FILE_PATH + " = '" + path + "'";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToNext())
                return true;
        }
        return false;
    }

    public ArrayList<FavouriteDto> readAllData(SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME;

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

    public void removeFavourite(SQLiteDatabase db, String id) {
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id);
    }
}
