package com.polytech.myvideo.listeners;

import android.view.View;

import com.polytech.myvideo.adapter.FileUIAdapter;

public class BackClickListener implements View.OnClickListener{

    private FileUIAdapter fileUIAdapter;

    public BackClickListener(FileUIAdapter fileUIAdapter) {
        this.fileUIAdapter = fileUIAdapter;
    }

    @Override
    public void onClick(View view) {
        fileUIAdapter.pushBack();
    }
}
