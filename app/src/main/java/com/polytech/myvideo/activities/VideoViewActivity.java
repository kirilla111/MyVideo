package com.polytech.myvideo.activities;

import android.os.Bundle;
import android.view.OrientationEventListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.polytech.myvideo.ComponentFactory;
import com.polytech.myvideo.R;
import com.polytech.myvideo.adapter.Utils;
import com.polytech.myvideo.listeners.AddToFavouriteClickListener;
import com.polytech.myvideo.listeners.MyOrientationEventListener;

import java.io.File;

public class VideoViewActivity extends AppCompatActivity {
    private ImageButton backButton, toFavouriteButton;
    private TextView tv_path, tv_name, tv_size;
    private VideoView videoView;
    private File file;
    private OrientationEventListener orientationEventListener;
    private LinearLayout contentLnearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        backButton = findViewById(R.id.back_button_activity);
        toFavouriteButton = findViewById(R.id.toFavouriteButton);
        tv_path = findViewById(R.id.textView_path);
        tv_name = findViewById(R.id.textView_name);
        tv_size = findViewById(R.id.textView_size);
        videoView = findViewById(R.id.videoView);
        contentLnearLayout = findViewById(R.id.content_linearLayout);

        File file = (File) getIntent().getExtras().get("File");

        tv_size.setText(String.format("%s%s", tv_size.getText(), Utils.getTotalFiles(file)));
        tv_path.setText(String.format("%s%s", tv_path.getText(), file.getParentFile().getAbsolutePath()));
        tv_name.setText(String.format("%s%s", tv_name.getText(), file.getName()));
        videoView.setVideoPath(file.getAbsolutePath());
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);


        backButton.setOnClickListener((v) -> finish());
        boolean isFavourite = ComponentFactory.getDbHelper().existByPath(file.getAbsolutePath());
        Utils.setToFavouriteButtonImage(isFavourite, toFavouriteButton);
        toFavouriteButton.setOnClickListener(new AddToFavouriteClickListener(file, isFavourite));

        orientationEventListener = new MyOrientationEventListener(this, contentLnearLayout);
        orientationEventListener.enable();

        ComponentFactory.getDbHelper().addNewHistory(file.getName(), file.getAbsolutePath());
    }
}