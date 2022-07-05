package com.polytech.myvideo.db.scripts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.polytech.myvideo.adapter.Utils;
import com.polytech.myvideo.db.dto.HistoryDto;

import java.sql.Timestamp;
import java.util.ArrayList;

public class HistoryTableCreationScript implements Script {
    private static final String HISTORY_TABLE_NAME = "history";
    private static final String HISTORY_COLUMN_ID = "_id";
    private static final String HISTORY_COLUMN_NAME = "file_name";
    private static final String HISTORY_COLUMN_PATH = "file_path";
    private static final String HISTORY_COLUMN_DATE = "date";

    @Override
    public void create(SQLiteDatabase db) {
        String query = "CREATE TABLE " + HISTORY_TABLE_NAME +
                " (" + HISTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HISTORY_COLUMN_NAME + " TEXT, " +
                HISTORY_COLUMN_PATH + " TEXT, " +
                HISTORY_COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void update(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE_NAME);
    }

    public ArrayList<HistoryDto> readHistory(SQLiteDatabase db) {
        String query = "SELECT * FROM " + HISTORY_TABLE_NAME + " ORDER BY " + HISTORY_COLUMN_ID + " DESC";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            ArrayList<HistoryDto> history = new ArrayList<>();
            while (cursor.moveToNext()) {
                history.add(new HistoryDto(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
            return history;
        }
        return null;
    }

    public void addNewHistory(SQLiteDatabase db, String fileName, String path, Context context) {
        removeByPath(db, path);

        ContentValues cv = new ContentValues();

        cv.put(HISTORY_COLUMN_NAME, fileName);
        cv.put(HISTORY_COLUMN_PATH, path);
        cv.put(HISTORY_COLUMN_DATE,  Utils.DATE_FORMAT.format(new Timestamp(System.currentTimeMillis())));

        long result = db.insert(HISTORY_TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add History", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Added to History", Toast.LENGTH_LONG).show();
        }
    }

    public void removeFromHistory(SQLiteDatabase db, String id) {
        db.execSQL("DELETE FROM " + HISTORY_TABLE_NAME + " WHERE " + HISTORY_COLUMN_ID + " = " + id);
    }

    private void removeByPath(SQLiteDatabase db, String path) {
        db.execSQL("DELETE FROM " + HISTORY_TABLE_NAME + " WHERE " + HISTORY_COLUMN_PATH + " = '" + path + "'");
    }

    public long totalVideosWatched(SQLiteDatabase db) {
        String query = "SELECT COUNT(*) FROM " + HISTORY_TABLE_NAME;
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            cursor.moveToNext();
            return cursor.getLong(0);
        }
        return 0;
    }
}
