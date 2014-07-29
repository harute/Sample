package com.example.imagebtnsample;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private Drawable imageData_;
    private String textData_;

    public void setImagaData(Drawable image) {
        imageData_ = image;
    }

    public Drawable getImageData() {
        return imageData_;
    }

    public void setTextData(String text) {
        textData_ = text;
    }

    public String getTextData() {
        return textData_;
    }
}
