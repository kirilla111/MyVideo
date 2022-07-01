package com.polytech.myvideo;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.polytech.myvideo.adapter.FileUIAdapter;
import com.polytech.myvideo.db.DbHelper;

public class ComponentFactory {
    private static FileUIAdapter conductorFileUIAdapter;
    private static FileUIAdapter favouriteFileUIAdapter;
    private static DbHelper dbHelper;
    private static ImageButton mainFavouriteImageButton;

    public static FileUIAdapter getConductorFileUIAdapter(){
        if (conductorFileUIAdapter == null){
            conductorFileUIAdapter = new FileUIAdapter();
        }

        return conductorFileUIAdapter;
    }

    public static DbHelper getDbHelper(){
        if (dbHelper == null){
            throw new Resources.NotFoundException();
        }

        return dbHelper;
    }

    public static void createDbHelper(Context context){
        if (dbHelper == null){
            dbHelper = new DbHelper(context);
        }
    }

    public static FileUIAdapter getFavouriteFileUIAdapter() {
        if (favouriteFileUIAdapter == null){
            favouriteFileUIAdapter = new FileUIAdapter();
        }

        return favouriteFileUIAdapter;
    }

    public static void setMainFavouriteImageButton(ImageButton ImageButton){
        mainFavouriteImageButton = ImageButton;
    }

    public static ImageButton getMainFavouriteImageButton(){
        return mainFavouriteImageButton;
    }
}
