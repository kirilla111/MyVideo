package com.polytech.myvideo.db.scripts;

import android.database.sqlite.SQLiteDatabase;

public interface Script {
    void create(SQLiteDatabase db);
    void update(SQLiteDatabase db);
}
