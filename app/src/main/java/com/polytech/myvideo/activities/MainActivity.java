package com.polytech.myvideo.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.R;
import com.polytech.myvideo.activities.tabs.ConductorFragment;
import com.polytech.myvideo.activities.tabs.FavoritesFragment;
import com.polytech.myvideo.adapter.FileUIAdapter;
import com.polytech.myvideo.adapter.TabAdapter;
import com.polytech.myvideo.adapter.Utils;
import com.polytech.myvideo.listeners.AddToFavouriteClickListener;
import com.polytech.myvideo.listeners.BackClickListener;
import com.polytech.myvideo.listeners.BackToBaseDirClickListener;
import com.polytech.myvideo.listeners.MyOrientationEventListener;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton toBaseDirButton, backButton, toFavouriteButton;
    private LinearLayout layoutTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        askForPermissions();
        ComponentFactory.createDbHelper(this);
        ComponentFactory.getDbHelper().readAllData();
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        toBaseDirButton = findViewById(R.id.return_to_gome_button);
        backButton = findViewById(R.id.back_button);
        toFavouriteButton = findViewById(R.id.to_favourite_button);
        layoutTools = findViewById(R.id.layout_tools);

        tabLayout.setupWithViewPager(viewPager);
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabAdapter.addFragment(new ConductorFragment(layoutTools), getString(R.string.tab_text_1));
        tabAdapter.addFragment(new FavoritesFragment(layoutTools), getString(R.string.tab_text_2));
        viewPager.setAdapter(tabAdapter);

        FileUIAdapter adapter = ComponentFactory.getConductorFileUIAdapter();
        toBaseDirButton.setOnClickListener(new BackToBaseDirClickListener(adapter));
        backButton.setOnClickListener(new BackClickListener(adapter));


        toFavouriteButton.setOnClickListener(new AddToFavouriteClickListener(adapter.currentDir, adapter, toFavouriteButton));

        OrientationEventListener orientationEventListener = new MyOrientationEventListener(this);
        orientationEventListener.enable();
    }

    private void askForPermissions() {
        while (
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                        && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 44);
        }
    }
}