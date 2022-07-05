package com.polytech.myvideo.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.polytech.myvideo.activities.components.FileItem;

public class SearchTextWatcher implements TextWatcher {
    private LinearLayout linearLayout;
    public SearchTextWatcher(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int count = linearLayout.getChildCount();
        for (int l = 0; l < count; l++) {
            View view = linearLayout.getChildAt(l);
            charSequence = charSequence.toString().toLowerCase();
            if (view instanceof FileItem){
                String name = ((FileItem) view).getFileName().toString().toLowerCase();
                if (name.contains(charSequence)){
                    view.setVisibility(View.VISIBLE);
                }else {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
