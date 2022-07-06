package com.polytech.myvideo.activities.components;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.polytech.myvideo.R;
import com.polytech.myvideo.db.dto.StatisticsDto;

public class StatisticsItem extends ConstraintLayout {
    private TextView start_tv;
    private TextView end_tv;
    private StatisticsDto dto;

    public StatisticsItem(@NonNull Context context, StatisticsDto dto) {
        super(context);
        inflate(context, R.layout.statistics_item, this);
        start_tv = findViewById(R.id.start_tv);
        end_tv = findViewById(R.id.end_tv);

        start_tv.setText(String.format(getContext().getString(R.string.dateStartFormat), dto.getStartDate()));
        end_tv.setText(String.format(getContext().getString(R.string.dateEndFormat), dto.getEndDate()));
    }
}
