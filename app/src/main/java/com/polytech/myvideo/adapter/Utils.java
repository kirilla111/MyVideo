package com.polytech.myvideo.adapter;

import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static android.content.ContentValues.TAG;

public class Utils {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static File[] readBaseDir() {
        File baseDir = Environment.getExternalStorageDirectory();
        return baseDir.listFiles();
    }

    public static void setToFavouriteButtonImage(boolean isFavourite, ImageButton imageButton) {
        if (isFavourite) {
            imageButton.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            imageButton.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    public static String getTotalFiles(File file) {
        if (file.isFile()) {
            return String.format("Обьем: %s Кбайт", file.length()/1024);
        } else {
            try {
                return String.format("Обьектов: %s", folderFilesSum(file));
            } catch (Exception e) {
                return "Нет информации";
            }
        }
    }

    public static long folderFilesSum(File directory) {
        int sum = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                sum += 1;
            else
                sum += folderFilesSum(file) + 1;
        }
        return sum;
    }

    public static LinkedList<File> filtredFileList(File[] files) {
        LinkedList<File> fileLinkedList = new LinkedList<>();
        try {
            for (File file : files) {
                if (file.isDirectory() || fileIsSupported(file)) fileLinkedList.add(file);
            }
            return fileLinkedList;
        } catch (Exception e) {
            return fileLinkedList;
        }
    }

    private static boolean fileIsSupported(File file) {
        for (String type : FileUIAdapter.SUPPORTED_MEDIA_TYPES) {
            String extension = getExtension(file.getAbsolutePath());
            if (extension.equals(type)) return true;
        }
        return false;
    }

    private static String getExtension(String absolutePath) {
        return absolutePath.substring(absolutePath.lastIndexOf("."));
    }
}
