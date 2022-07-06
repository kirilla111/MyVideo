package com.polytech.myvideo.activities.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.R;
import com.polytech.myvideo.activities.components.StatisticsItem;
import com.polytech.myvideo.db.dto.StatisticsDto;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {
    private LinearLayout layoutTools;
    private ProgressBar progressBar;
    private View rootView;
    private LinearLayout statisticLayout;
    private TextView totalTime_tv, totalWatched_tv, maxSession_tv;

    public StatisticsFragment(LinearLayout layoutTools, ProgressBar progressBar) {
        this.layoutTools = layoutTools;
        this.progressBar = progressBar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        return rootView;
    }

    private void setStatisticsLayout() {
        progressBar.setVisibility(View.VISIBLE);
        statisticLayout = rootView.findViewById(R.id.statistics_linear_layout);
        totalTime_tv = rootView.findViewById(R.id.totalTime_tv);
        totalWatched_tv = rootView.findViewById(R.id.totalWatched_tv);
        maxSession_tv = rootView.findViewById(R.id.recordSession_tv);
        statisticLayout.removeAllViews();
        ArrayList<StatisticsDto> statisticsDtos = ComponentFactory.getDbHelper().getStatisticsData();

        for (StatisticsDto dto : statisticsDtos) {
            StatisticsItem item = new StatisticsItem(rootView.getContext(), dto);
            statisticLayout.addView(item);
        }

        long time = ComponentFactory.getDbHelper().getMaxTime();
        long totalTime = ComponentFactory.getDbHelper().getTotalTime();
        long totalVideoWatched =  ComponentFactory.getDbHelper().totalVideosWatched();

        String maxSessionText = String.format(rootView.getContext().getString(R.string.recordSession), parseTime(time));
        String totalTimeText = String.format(rootView.getContext().getString(R.string.timeWatchedTotal), parseTime(totalTime));
        String totalVideoWatchedText = String.format(rootView.getContext().getString(R.string.watchedTotal), totalVideoWatched);
        maxSession_tv.setText(maxSessionText);
        totalTime_tv.setText(totalTimeText);
        totalWatched_tv.setText(totalVideoWatchedText);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private String parseTime(long timeInSec) {
        if (timeInSec > 60 * 60) {
            return timeInSec / 60 / 60 + " ч";
        }
        if (timeInSec > 60)
            return timeInSec / 60 + " мин";
        return timeInSec + " сек";
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutTools.setVisibility(View.GONE);
        setStatisticsLayout();
    }
}