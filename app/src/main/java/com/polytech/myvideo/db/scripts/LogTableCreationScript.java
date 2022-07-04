package com.polytech.myvideo.db.scripts;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polytech.myvideo.db.StatisticsDto;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LogTableCreationScript implements Script {
    private static final String LOG_TABLE_NAME = "logs";
    private static final String LOG_COLUMN_ID = "_id";
    private static final String LOG_ENTER_DATE_TIME = "enter_date";
    private static final String LOG_EXIT_DATE_TIME = "exit_date";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Timestamp enterDateTime;
    private Timestamp exitDateTime;

    @Override
    public void create(SQLiteDatabase db) {
        String query = "CREATE TABLE " + LOG_TABLE_NAME +
                " (" + LOG_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LOG_ENTER_DATE_TIME + " TEXT, " +
                LOG_EXIT_DATE_TIME + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void update(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + LOG_TABLE_NAME);
    }

    public void startStoreStatistics() {
        enterDateTime = new Timestamp(System.currentTimeMillis());
    }

    public void storeStatistic(SQLiteDatabase db) {
        exitDateTime = new Timestamp(System.currentTimeMillis());

        ContentValues cv = new ContentValues();
        cv.put(LOG_ENTER_DATE_TIME, sdf.format(enterDateTime));
        cv.put(LOG_EXIT_DATE_TIME, sdf.format(exitDateTime));
        db.insert(LOG_TABLE_NAME, null, cv);
    }

    public ArrayList<StatisticsDto> getStatisticsData(SQLiteDatabase db) {
        String query = "SELECT * FROM " + LOG_TABLE_NAME + " ORDER BY " + LOG_COLUMN_ID + " DESC";;

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            ArrayList<StatisticsDto> statisticsDtos = new ArrayList<>();
            while (cursor.moveToNext()) {
                statisticsDtos.add(new StatisticsDto(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
            return statisticsDtos;
        }
        return null;
    }

    public long getMaxTime(SQLiteDatabase db) {
        ArrayList<StatisticsDto> statisticsDtos = getStatisticsData(db);
        long maxTime = 0;
        for (StatisticsDto dto : statisticsDtos) {
            try {
                Date startDate = sdf.parse(dto.getStartDate());
                Date endDate = sdf.parse(dto.getEndDate());
                long time = endDate.getTime() - startDate.getTime();

                if (time>maxTime){
                    maxTime = time;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return maxTime/1000;
    }

    public long getTotalTime(SQLiteDatabase db) {
        ArrayList<StatisticsDto> statisticsDtos = getStatisticsData(db);
        long totalTime = 0;
        for (StatisticsDto dto : statisticsDtos) {
            try {
                Date startDate = sdf.parse(dto.getStartDate());
                Date endDate = sdf.parse(dto.getEndDate());
                long time = endDate.getTime() - startDate.getTime();
                totalTime += time;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return totalTime/1000;
    }
}
