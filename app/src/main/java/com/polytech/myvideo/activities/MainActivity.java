package com.polytech.myvideo.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.R;
import com.polytech.myvideo.activities.tabs.ConductorFragment;
import com.polytech.myvideo.activities.tabs.FavoritesFragment;
import com.polytech.myvideo.activities.tabs.HistoryFragment;
import com.polytech.myvideo.activities.tabs.StatisticsFragment;
import com.polytech.myvideo.adapter.FileUIAdapter;
import com.polytech.myvideo.adapter.TabAdapter;
import com.polytech.myvideo.listeners.AddToFavouriteClickListener;
import com.polytech.myvideo.listeners.BackClickListener;
import com.polytech.myvideo.listeners.BackToBaseDirClickListener;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton toBaseDirButton, backButton, toFavouriteButton;
    private LinearLayout layoutTools;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askForPermissions();
        ComponentFactory.createDbHelper(this);
        ComponentFactory.getDbHelper().readAllData();
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        toBaseDirButton = findViewById(R.id.return_to_gome_button);
        backButton = findViewById(R.id.back_button);
        toFavouriteButton = findViewById(R.id.to_favourite_button);
        layoutTools = findViewById(R.id.layout_tools);
        progressBar = findViewById(R.id.progressBar);

        tabLayout.setupWithViewPager(viewPager);
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabAdapter.addFragment(new ConductorFragment(layoutTools, progressBar), getString(R.string.tab_text_1));
        tabAdapter.addFragment(new FavoritesFragment(layoutTools, progressBar), getString(R.string.tab_text_2));
        tabAdapter.addFragment(new HistoryFragment(layoutTools, progressBar), getString(R.string.history_tab_text));
        tabAdapter.addFragment(new StatisticsFragment(layoutTools, progressBar), getString(R.string.tab_statistics_title));
        viewPager.setAdapter(tabAdapter);

        FileUIAdapter adapter = ComponentFactory.getConductorFileUIAdapter();
        toBaseDirButton.setOnClickListener(new BackToBaseDirClickListener(adapter));
        backButton.setOnClickListener(new BackClickListener(adapter));


        toFavouriteButton.setOnClickListener(new AddToFavouriteClickListener(adapter.currentDir, adapter, toFavouriteButton));
        ComponentFactory.getDbHelper().startStoreStatistics();
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

    @Override
    protected void onStop() {
        super.onStop();
        ComponentFactory.getDbHelper().storeStatistics();
        ComponentFactory.getDbHelper().startStoreStatistics();
    }
}