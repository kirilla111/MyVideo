package com.polytech.myvideo.listeners;

import android.view.View;

import com.polytech.myvideo.adapter.FileUIAdapter;

import java.io.File;

public class DirClickListener implements View.OnClickListener {
    private FileUIAdapter fileUIAdapter;
    private File file;

    public DirClickListener(FileUIAdapter fileUIAdapter, File file) {
        this.fileUIAdapter = fileUIAdapter;
        this.file = file;
    }

    @Override
    public void onClick(View view) {
        fileUIAdapter.push(file.getAbsolutePath());
    }
}
