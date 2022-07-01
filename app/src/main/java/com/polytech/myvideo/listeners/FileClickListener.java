package com.polytech.myvideo.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.polytech.myvideo.activities.VideoViewActivity;

import java.io.File;

public class FileClickListener implements View.OnClickListener {
    private File file;

    public FileClickListener(File file) {
        this.file = file;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, VideoViewActivity.class);
        intent.putExtra("File", file);
        context.startActivity(intent);
    }
}
