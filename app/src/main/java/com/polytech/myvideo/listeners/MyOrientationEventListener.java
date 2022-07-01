package com.polytech.myvideo.listeners;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.OrientationEventListener;
import android.view.View;

public class MyOrientationEventListener extends OrientationEventListener {
    View[] viewsToBeGone;
    private Activity activity;

    public MyOrientationEventListener(Activity activity, View... viewsToBeGone) {
        super(activity);
        this.activity = activity;
        this.viewsToBeGone = viewsToBeGone;
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (orientation == 0) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setVisibility(View.VISIBLE);
        } else if (orientation == 90) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            setVisibility(View.GONE);
        } else if (orientation == 180) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            setVisibility(View.VISIBLE);
        } else if (orientation == 270) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setVisibility(View.GONE);
        }
    }

    private void setVisibility(int visibility) {
        for (View view : viewsToBeGone) {
            view.setVisibility(visibility);
        }
    }
}
