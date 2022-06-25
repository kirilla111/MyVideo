package com.polytech.myvideo;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.polytech.myvideo.tabs.ConductorFragment;
import com.polytech.myvideo.tabs.FavoritesFragment;
import com.polytech.myvideo.tabs.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);

        Adapter adapter = new Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new ConductorFragment(), getString(R.string.tab_text_1));
        adapter.addFragment(new FavoritesFragment(), getString(R.string.tab_text_2));
        viewPager.setAdapter(adapter);
    }
}