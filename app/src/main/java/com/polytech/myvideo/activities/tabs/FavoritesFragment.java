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
import com.polytech.myvideo.db.DbHelper;
import com.polytech.myvideo.db.FavouriteDto;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    private FileUIAdapter fileUIAdapter = ComponentFactory.getFavouriteFileUIAdapter();
    private DbHelper dbHelper = ComponentFactory.getDbHelper();
    private LinearLayout fileLayout;
    private LinearLayout layoutTools;
    private View rootView;

    public FavoritesFragment(LinearLayout layoutTools) {
        this.layoutTools = layoutTools;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        fileLayout = rootView.findViewById(R.id.favourite_linear_layout);

        ArrayList<FavouriteDto> dtos = dbHelper.readAllData();
        fileUIAdapter.setFavouriteFileItems(getContext(), fileLayout, dtos);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<FavouriteDto> dtos = dbHelper.readAllData();
        fileUIAdapter.setFavouriteFileItems(getContext(), fileLayout, dtos);
        layoutTools.setVisibility(View.GONE);
    }
}