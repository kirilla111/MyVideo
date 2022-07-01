package com.polytech.myvideo.activities.components;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.polytech.myvideo.R;
import com.polytech.myvideo.adapter.FileUIAdapter;
import com.polytech.myvideo.adapter.Utils;
import com.polytech.myvideo.db.FavouriteDto;
import com.polytech.myvideo.listeners.DirClickListener;
import com.polytech.myvideo.listeners.FileClickListener;

import java.io.File;

public class FileItem extends ConstraintLayout {

    private TextView fileInfo;
    private TextView fileName;
    private ImageView fileImg;
    private boolean isVideoFile;

    public FileItem(@NonNull Context context, File file, FileUIAdapter fileUIAdapter) {
        super(context);
        inflate(context, R.layout.file_item, this);
        fileInfo = findViewById(R.id.add_file_info_tv);
        fileName = findViewById(R.id.file_name_tv);
        fileImg = findViewById(R.id.file_img);
        isVideoFile = file.isFile();

        fileName.setText(file.getName());
        fileInfo.setText(R.string.calculating_string);

        fileInfo.post(new Runnable() {
            public void run() {
                fileInfo.setText(Utils.getTotalFiles(file));
            }
        });
        setFileConfig(file, fileUIAdapter);
    }

    public FileItem(Context context, FavouriteDto dto, FileUIAdapter fileUIAdapter) {
        super(context);
        inflate(context, R.layout.file_item, this);

        File file = new File(dto.getPath());
        fileInfo = findViewById(R.id.add_file_info_tv);
        fileName = findViewById(R.id.file_name_tv);
        fileImg = findViewById(R.id.file_img);
        isVideoFile = isFile(dto.getName());

        fileName.setText(dto.getName());
        fileInfo.setText(R.string.calculating_string);

        fileInfo.post(new Runnable() {
            public void run() {
                fileInfo.setText(Utils.getTotalFiles(file));
            }
        });
        setFileConfig(file, fileUIAdapter);
    }

    private boolean isFile(String name) {
        return name.contains(".");
    }

    private void setFileConfig(File file, FileUIAdapter adapter) {
        if (isVideoFile){
            fileImg.setImageResource(R.drawable.file);
            setOnClickListener(new FileClickListener(file));
        }else {
            fileImg.setImageResource(R.drawable.folder);
            setOnClickListener(new DirClickListener(adapter, file));
        }
    }
}
