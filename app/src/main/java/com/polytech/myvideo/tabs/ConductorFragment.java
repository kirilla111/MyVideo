package com.polytech.myvideo.tabs;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.polytech.myvideo.R;

import java.io.File;

public class ConductorFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conductor, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
    }

    private File[] readBaseDir() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            File baseDir = Environment.getExternalStorageDirectory();
            return baseDir.listFiles();
        } else {
            Toast toast = Toast.makeText(getContext(),
                    R.string.ERROR_READING_MESSAGE, Toast.LENGTH_SHORT);
            toast.show();
            throw new Resources.NotFoundException();
        }
    }
}