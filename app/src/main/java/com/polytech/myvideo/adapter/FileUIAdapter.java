package com.polytech.myvideo.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.polytech.myvideo.activities.components.FileItem;
import com.polytech.myvideo.db.FavouriteDto;

import java.io.File;
import java.util.ArrayList;

public class FileUIAdapter {
    public static String[] SUPPORTED_MEDIA_TYPES = {".mp4", ".avi", ".mkv"};

    public String currentPath;
    public File currentDir;
    public String basePath;
    private LinearLayout fileLayout;
    private Context context;

    public void setConductorFileItems(Context context, LinearLayout fileLayout, File[] files) {
        this.fileLayout = fileLayout;
        this.context = context;

        if (files.length > 0) {
            basePath = files[0].getParentFile().getPath();
            currentDir = files[0].getParentFile();
            synchronized (this) {
                this.notify();
            }
        }

        for (File file : Utils.filtredFileList(files)) {
            FileItem item = new FileItem(context, file, this);
            fileLayout.addView(item);
        }
    }

    public void push(String absolutePath) {
        FileUIAdapter self = this;
        File dir = new File(absolutePath);
        currentDir = dir;
        currentPath = absolutePath;
        fileLayout.removeAllViews();

        synchronized (this) {
            this.notify();
        }
        fileLayout.post(new Runnable() {
            public void run() {
                for (File file : Utils.filtredFileList(dir.listFiles())) {
                    FileItem item = new FileItem(context, file, self);
                    fileLayout.addView(item);
                }
                if (fileLayout.getChildCount() == 0) {
                    Toast.makeText(context, "Поддерживаемых медиа файлов не обнаружено", Toast.LENGTH_LONG).show();
                }
            }
        });
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

    public void setFavouriteFileItems(Context context, LinearLayout fileLayout, ArrayList<FavouriteDto> dtos) {
        this.fileLayout = fileLayout;
        this.context = context;
        fileLayout.removeAllViews();

        for (FavouriteDto dto : dtos) {
            FileItem item = new FileItem(context, dto, this);
            fileLayout.addView(item);
        }
    }
}
