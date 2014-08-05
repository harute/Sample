package com.example.imagebtnsample;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

        // ボタン押下時に画面遷移 sample
        ImageButton btn = (ImageButton)findViewById(R.id.imgbtn_id01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // ボタン押下時に画面遷移 sample
        ImageButton btn2 = (ImageButton)findViewById(R.id.imgbtn_id02);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // ここからList処理
        listView1 = (ListView)findViewById(R.id.listView1);

        // http処理
        Http.Request request = new Http.Request();
        request.url = "https://but-pazu-test.ssl-lolipop.jp/skillSearch.php?leader[0]=1200&leader[1]=1201&member[0]=1234&member[1]=1235&member[2]=1236&member[3]=1236";

        Http.Response response = Http.requestSync(request, StringResponseHandler.getInstance());
        Sample sample = new Sample();
        if (response.code == 200) {
            String str = (String) response.value;
            Gson gson = new Gson();
            sample = gson.fromJson(str, Sample.class);
        }

        @SuppressWarnings("unchecked")
        ArrayList<String>[] arrtest = new ArrayList[50];
        arrtest[0] = new ArrayList<String>();
        arrtest[0].add(sample.ls_desc.split(",")[0] + "\n" + sample.ls_desc.split(",")[1]);
        arrtest[0].add("http://pazu-test.but.jp/image/004.png");

        // 覚醒
        int cnt = 1;
        for (Kakusei kakuraw : sample.kakusei) {
            arrtest[cnt] = new ArrayList<String>();
            arrtest[cnt].add(kakuraw.desc);
            arrtest[cnt].add(kakuraw.url);
            cnt++;
        }

        // ここで繰り返し処理
        //入力ストリームを開く
        InputStream istream;
        try {
            for (int i = 0; i < cnt; i++) {
                URL url = new URL(arrtest[i].get(1));
                istream = url.openStream();
                Drawable d = Drawable.createFromStream(istream, "webimg");
                AppInfo bbb = new AppInfo();
                bbb.setTextData(arrtest[i].get(0));
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

/*
* Json文字列をこのクラスに変化するa
*/
class Sample{
    public String ls_name;
    public String ls_desc;
    public Kakusei[] kakusei;
}

class Kakusei {
    public String desc;
    public String url;
}