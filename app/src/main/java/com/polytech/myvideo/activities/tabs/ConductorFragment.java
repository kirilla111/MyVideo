package com.polytech.myvideo.activities.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.R;
import com.polytech.myvideo.adapter.FileUIAdapter;
import com.polytech.myvideo.adapter.Utils;
import com.polytech.myvideo.listeners.SearchTextWatcher;

import java.io.File;

public class ConductorFragment extends Fragment {
    private FileUIAdapter fileUIAdapter = ComponentFactory.getConductorFileUIAdapter();
    private View rootView;
    private LinearLayout layoutTools;
    private ProgressBar progressBar;
    private EditText search;
    private TextView path_tv;

    public ConductorFragment(LinearLayout layoutTools, ProgressBar progressBar) {
        this.layoutTools = layoutTools;
        this.progressBar = progressBar;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conductor, container, false);
        LinearLayout fileLayout = rootView.findViewById(R.id.conductor_linear_layout);
        search = rootView.findViewById(R.id.conductor_search_tv);
        path_tv = rootView.findViewById(R.id.path_tv);
        fileUIAdapter.path_tv = path_tv;

        File[] files = Utils.readBaseDir();
        fileLayout.post(() ->fileUIAdapter.setConductorFileItems(getContext(), fileLayout, files, progressBar));
        search.addTextChangedListener(new SearchTextWatcher(fileLayout));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutTools.setVisibility(View.VISIBLE);
    }
}