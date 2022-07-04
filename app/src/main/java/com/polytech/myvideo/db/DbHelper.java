package com.polytech.myvideo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.polytech.myvideo.db.scripts.FavouriteTableCreationScript;
import com.polytech.myvideo.db.scripts.HistoryTableCreationScript;
import com.polytech.myvideo.db.scripts.LogTableCreationScript;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyVideo.db";
    private static final int DATABASE_VERSION = 5;

    private final FavouriteTableCreationScript favouriteScript = new FavouriteTableCreationScript();
    private final LogTableCreationScript logScript = new LogTableCreationScript();
    private final HistoryTableCreationScript historyScript = new HistoryTableCreationScript();

    private Context context;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        favouriteScript.create(db);
        historyScript.create(db);
        logScript.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        favouriteScript.update(db);
        historyScript.update(db);
        logScript.update(db);
        onCreate(db);
    }

    public void addFavourite(String fileName, String path) {
        SQLiteDatabase db = this.getWritableDatabase();
        favouriteScript.addToFavourite(db, fileName, path, context);
    }

    public boolean existByPath(String path) {
        SQLiteDatabase db = this.getReadableDatabase();
        return favouriteScript.existByPath(db, path);
    }

    public ArrayList<FavouriteDto> readAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return favouriteScript.readAllData(db);
    }

    public void removeFavourite(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        favouriteScript.removeFavourite(db, id);
    }

    public ArrayList<FavouriteDto> readHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return historyScript.readHistory(db);
    }

    public void addNewHistory(String fileName, String path) {
        SQLiteDatabase db = this.getWritableDatabase();
        historyScript.addNewHistory(db, fileName, path, context);
    }

    public void removeFromHistory(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        historyScript.removeFromHistory(db, id);
    }

    public void startStoreStatistics() {
        logScript.startStoreStatistics();
    }

    public void storeStatistics() {
        SQLiteDatabase db = this.getWritableDatabase();
        logScript.storeStatistic(db);
    }

    public ArrayList<StatisticsDto> getStatisticsData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return logScript.getStatisticsData(db);
    }

    public long getMaxTime() {
        SQLiteDatabase db = this.getReadableDatabase();
        return logScript.getMaxTime(db);
    }

    public long getTotalTime() {
        SQLiteDatabase db = this.getReadableDatabase();
        return logScript.getTotalTime(db);
    }

    public long totalVideosWatched(){
        SQLiteDatabase db = this.getReadableDatabase();
        return historyScript.totalVideosWatched(db);
    }
}
