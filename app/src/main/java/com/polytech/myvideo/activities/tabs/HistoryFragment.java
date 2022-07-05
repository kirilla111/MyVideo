package com.polytech.myvideo.activities.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.R;
import com.polytech.myvideo.activities.components.FileItem;
import com.polytech.myvideo.activities.components.HistoryFileItem;
import com.polytech.myvideo.adapter.FileUIAdapter;
import com.polytech.myvideo.db.dto.FavouriteDto;
import com.polytech.myvideo.db.dto.HistoryDto;
import com.polytech.myvideo.listeners.SearchTextWatcher;

public class HistoryFragment extends Fragment {
    private LinearLayout fileLayout;
    private View rootView;
    private ProgressBar progressBar;
    private LinearLayout layoutTools;
    private FileUIAdapter adapter = new FileUIAdapter();
    private EditText search;

    public HistoryFragment(LinearLayout layoutTools, ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.layoutTools = layoutTools;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);
        fileLayout = rootView.findViewById(R.id.history_linear_layout);
        search = rootView.findViewById(R.id.history_search_tv);
        search.addTextChangedListener(new SearchTextWatcher(fileLayout));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fileLayout.post(this::setHistory);
        layoutTools.setVisibility(View.GONE);
    }

    private void setHistory() {
        fileLayout.removeAllViews();

        for (HistoryDto dto : ComponentFactory.getDbHelper().readHistory()) {
            HistoryFileItem item = new HistoryFileItem(rootView.getContext(), dto);
            item.setOnLongClickListener((view) -> {
                fileLayout.removeView(view);
                ComponentFactory.getDbHelper().removeFromHistory(dto.getId());
                Toast.makeText(rootView.getContext(), "Удалено из истории!", Toast.LENGTH_LONG).show();
                return true;
            });
            fileLayout.addView(item);
        }
        if (fileLayout.getChildCount() == 0) {
            Toast.makeText(rootView.getContext(), "История пуста!", Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }
}