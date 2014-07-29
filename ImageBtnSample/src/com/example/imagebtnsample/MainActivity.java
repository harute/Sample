package com.example.imagebtnsample;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {

    int width;
    int height;

    static List<AppInfo> items = new ArrayList<AppInfo>();
    static AppInfoArrayAdapter adapter;

    ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //StrictModeを設定 penaltyDeathを取り除く
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        // ウィンドウタイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        getDisplay();
        setImageButton(R.id.imgbtn_id01, "001.png");
        setImageButton(R.id.imgbtn_id02, "002.png");
        setImageButton(R.id.imgbtn_id03, "003.png");
        setImageButton(R.id.imgbtn_id04, "004.png");
        setImageButton(R.id.imgbtn_id05, "002.png");
        setImageButton(R.id.imgbtn_id06, "002.png");

        // ボタン押下時に画面遷移
        ImageButton btn = (ImageButton)findViewById(R.id.imgbtn_id01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tag", "OKOK");
            }
        });

        // ボタン押下時に画面遷移
        ImageButton btn2 = (ImageButton)findViewById(R.id.imgbtn_id02);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tag", "OKOK222");
            }
        });

        // ここからList処理
        listView1 = (ListView)findViewById(R.id.listView1);

        String[][] lst = {
                {"11111111111111111111111111111111111111111aaaaaaaaaaaaaa", "http://pazu-test.but.jp/image/004.png"},
                {"OK", "http://pazu-test.but.jp/image/003.png"},
                {"NG", "http://pazu-test.but.jp/image/002.png"},
                {"NG1345", "http://pazu-test.but.jp/image/001.png"}
        };
        // ここで繰り返し処理
        //入力ストリームを開く
        InputStream istream;
        try {
            for (int i = 0; i < lst.length; i++) {
                URL url = new URL(lst[i][1]);
                istream = url.openStream();
                Drawable d = Drawable.createFromStream(istream, "webimg");
                AppInfo bbb = new AppInfo();
                bbb.setTextData(lst[i][0]);
                bbb.setImagaData(d);
                items.add(bbb);
            }
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }


        //adapter = new AppInfoArrayAdapter(this, R.layout.raw, R.id.row_textview1, items);
        adapter = new AppInfoArrayAdapter(this, R.layout.raw, items);
        listView1.setAdapter(adapter);

    }

    private void setImageButton(int imgbtnId, String fileName) {
        ImageButton img = (ImageButton)findViewById(imgbtnId);

        // 画像のURL
        String urlString="http://pazu-test.but.jp/image/" + fileName;

        try {
            //URLクラス
            URL url = new URL(urlString);
            //入力ストリームを開く
            InputStream istream = url.openStream();

            //画像をDrawableで取得
            Drawable d = Drawable.createFromStream(istream, "webimg");

            //入力ストリームを閉じる
            istream.close();

            img.setImageDrawable(d);
            android.view.ViewGroup.LayoutParams params = img.getLayoutParams();
            params.height = width / 6;
            params.width = width / 6;

            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } catch (Exception e) {
            System.out.println("nuu: "+e);
        }

    }

    private void getDisplay() {
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        // ディスプレイのインスタンス生成
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        width = size.x;
        height = size.y;
    }
}
