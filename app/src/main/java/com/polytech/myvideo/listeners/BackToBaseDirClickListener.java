package com.polytech.myvideo.listeners;

import android.view.View;

import com.polytech.myvideo.adapter.FileUIAdapter;

public class BackToBaseDirClickListener implements View.OnClickListener {
    private FileUIAdapter fileUIAdapter;

    public BackToBaseDirClickListener(FileUIAdapter fileUIAdapter) {
        this.fileUIAdapter = fileUIAdapter;
    }

    @Override
    public void onClick(View view) {
        fileUIAdapter.pushBaseDir();
    }
}
