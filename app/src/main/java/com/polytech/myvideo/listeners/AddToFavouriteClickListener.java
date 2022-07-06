package com.polytech.myvideo.listeners;

import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatImageButton;

import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.adapter.FileUIAdapter;

import java.io.File;

public class AddToFavouriteClickListener extends Thread implements View.OnClickListener {
    private File file;
    private boolean isFavourite;
    private FileUIAdapter adapter;
    private ImageButton imageButton;

    public AddToFavouriteClickListener(File file, boolean isFavourite) {
        this.file = file;
        this.isFavourite = isFavourite;
    }

    public AddToFavouriteClickListener(File file, FileUIAdapter adapter, ImageButton imageButton) {
        this.file = file;
        this.adapter = adapter;
        this.imageButton = imageButton;
        this.start();
    }

    @Override
    public void onClick(View view) {
        if (view.getClass().equals(AppCompatImageButton.class)) {
            setFavourite((ImageButton) view);
            isFavourite = !isFavourite;
        }
    }

    public void run() {
        updateFavourite();
        while (true) {
            updateFavourite();
        }
    }

    private boolean getFavourite() {
        return ComponentFactory.getDbHelper().existByPath(file.getAbsolutePath());
    }


    private void updateFavourite() {
        synchronized (adapter) {
            try {
                adapter.wait();
                file = adapter.currentDir;
                isFavourite = getFavourite();
                setFavourite();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * По notify (обновлению списка файлов)
     * Только для папок
     */
    private void setFavourite(){
        if (getFavourite()) {
            this.imageButton.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            this.imageButton.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
    /**
     * По клику
     * Только для файлов
     */
    private void setFavourite(ImageButton imageButton) {
        if (isFavourite) {
            imageButton.setImageResource(android.R.drawable.btn_star_big_off);
            ComponentFactory.getDbHelper().removeFavouriteByPath(file.getAbsolutePath());
        } else {
            imageButton.setImageResource(android.R.drawable.btn_star_big_on);
            ComponentFactory.getDbHelper().addFavourite(file.getName(), file.getAbsolutePath());
        }
    }
}
