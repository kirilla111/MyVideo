package com.polytech.myvideo.activities.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.R;
import com.polytech.myvideo.adapter.FileUIAdapter;
import com.polytech.myvideo.db.DbHelper;
import com.polytech.myvideo.db.dto.FavouriteDto;
import com.polytech.myvideo.listeners.SearchTextWatcher;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    private FileUIAdapter fileUIAdapter = ComponentFactory.getFavouriteFileUIAdapter();
    private DbHelper dbHelper = ComponentFactory.getDbHelper();
    private LinearLayout fileLayout;
    private LinearLayout layoutTools;
    private View rootView;
    private FloatingActionButton backButton;
    private ProgressBar progressBar;
    private EditText search;

    public FavoritesFragment(LinearLayout layoutTools, ProgressBar progressBar) {
        this.layoutTools = layoutTools;
        this.progressBar = progressBar;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        fileLayout = rootView.findViewById(R.id.history_linear_layout);
        backButton = rootView.findViewById(R.id.favourite_back_button);
        search = rootView.findViewById(R.id.favourite_search_tv);

        search.addTextChangedListener(new SearchTextWatcher(fileLayout));
        backButton.setOnClickListener((view) -> {
            setFavourite();
        });
        backButton.bringToFront();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fileLayout.post(this::setFavourite);
        layoutTools.setVisibility(View.GONE);
    }

    private void setFavourite() {
        ArrayList<FavouriteDto> dtos = dbHelper.readAllData();
        fileUIAdapter.setFileItemsByDto(getContext(), fileLayout, dtos, progressBar);
    }
}
