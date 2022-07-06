package com.polytech.myvideo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.activities.components.FileItem;
import com.polytech.myvideo.db.dto.FavouriteDto;

import java.io.File;
import java.util.ArrayList;

public class FileUIAdapter {
    public static String[] SUPPORTED_MEDIA_TYPES = {".mp4", ".avi", ".mkv"};

    public String currentPath;
    public TextView path_tv;
    public File currentDir;
    public String basePath;
    private LinearLayout fileLayout;
    private Context context;
    private ProgressBar progressBar;

    public void setConductorFileItems(Context context, LinearLayout fileLayout, File[] files, ProgressBar progressBar) {
        this.fileLayout = fileLayout;
        this.context = context;
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
        if (files.length > 0) {
            basePath = files[0].getParentFile().getPath();
            currentDir = files[0].getParentFile();
            synchronized (this) {
                this.notify();
            }
        }
        path_tv.setText(basePath);

        for (File file : Utils.filtredFileList(files)) {
            FileItem item = new FileItem(context, file, this);
            fileLayout.addView(item);
        }

        progressBar.setVisibility(View.INVISIBLE);
    }

    public void push(String absolutePath) {
        progressBar.setVisibility(View.VISIBLE);
        FileUIAdapter self = this;
        File dir = new File(absolutePath);
        currentDir = dir;
        currentPath = absolutePath;
        fileLayout.removeAllViews();
        if (path_tv != null) path_tv.setText(currentPath);
        synchronized (this) {
            this.notify();
        }
        for (File file : Utils.filtredFileList(dir.listFiles())) {
            FileItem item = new FileItem(context, file, self);
            fileLayout.addView(item);
        }
        if (fileLayout.getChildCount() == 0) {
            Toast.makeText(context, "Поддерживаемых медиа файлов не обнаружено", Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void pushBaseDir() {
        push(basePath);
    }

    public void pushBack() {
        if (currentPath == null) return;

        String path = currentPath.substring(0, currentPath.lastIndexOf(File.separator));

        if (path.length() < basePath.length())
            return;

        push(path);
    }

    public void setFileItemsByDto(Context context, LinearLayout fileLayout, ArrayList<FavouriteDto> dtos, ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
        this.fileLayout = fileLayout;
        this.context = context;
        fileLayout.removeAllViews();

        for (FavouriteDto dto : dtos) {
            FileItem item = new FileItem(context, dto, FileUIAdapter.this);
            item.setOnLongClickListener((view) -> {
                fileLayout.removeView(view);
                ComponentFactory.getDbHelper().removeFavourite(dto.getId());
                Toast.makeText(context, "Удалено!", Toast.LENGTH_LONG).show();
                return true;
            });
            fileLayout.addView(item);
        }
        if (fileLayout.getChildCount() == 0) {
            Toast.makeText(context, "Вы еще не добавили ничего в избранное!", Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }
}
