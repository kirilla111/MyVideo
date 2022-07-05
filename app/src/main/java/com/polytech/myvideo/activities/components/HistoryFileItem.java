package com.polytech.myvideo.activities.components;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.polytech.myvideo.R;
import com.polytech.myvideo.adapter.FileUIAdapter;
import com.polytech.myvideo.db.dto.FavouriteDto;
import com.polytech.myvideo.db.dto.HistoryDto;

import java.io.File;

public class HistoryFileItem extends FileItem{

    private TextView history_date;
    public HistoryFileItem(Context context, HistoryDto dto) {
        super(context, dto);

        history_date = findViewById(R.id.history_date);
        history_date.setVisibility(VISIBLE);
        history_date.setText(String.format((String) history_date.getText(),dto.getDate()));
    }
}
