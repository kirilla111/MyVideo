package com.polytech.myvideo.activities.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.R;
import com.polytech.myvideo.adapter.FileUIAdapter;
import com.polytech.myvideo.adapter.Utils;
import com.polytech.myvideo.db.FavouriteDto;
import com.polytech.myvideo.listeners.AddToFavouriteClickListener;

import java.io.File;
import java.util.ArrayList;

public class ConductorFragment extends Fragment {
    private FileUIAdapter fileUIAdapter = ComponentFactory.getConductorFileUIAdapter();
    private View rootView;
    private LinearLayout layoutTools;

    public ConductorFragment(LinearLayout layoutTools) {
        this.layoutTools = layoutTools;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conductor, container, false);
        LinearLayout fileLayout = rootView.findViewById(R.id.conductor_linear_layout);

        File[] files = Utils.readBaseDir();
        fileUIAdapter.setConductorFileItems(getContext(), fileLayout, files);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutTools.setVisibility(View.VISIBLE);
    }
}