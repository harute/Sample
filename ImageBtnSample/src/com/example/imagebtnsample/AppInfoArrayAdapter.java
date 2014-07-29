package com.example.imagebtnsample;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppInfoArrayAdapter extends ArrayAdapter<AppInfo> {
    private List<AppInfo> items;
    private LayoutInflater inflater;

    public AppInfoArrayAdapter(Context context, int resID, List<AppInfo> items) {
        super(context, resID, items);
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = inflater.inflate(R.layout.raw, null);
        }

        // 文字列をセット
        AppInfo appInfo = (AppInfo)items.get(position);
        TextView appInfoText = (TextView)v.findViewById(R.id.row_textview1);
        appInfoText.setText(appInfo.getTextData());

        // アイコンをセット
        ImageView appInfoImage = (ImageView)v.findViewById(R.id.row_imageview1);
        appInfoImage.setImageDrawable(appInfo.getImageData());

        return v;
    }
}