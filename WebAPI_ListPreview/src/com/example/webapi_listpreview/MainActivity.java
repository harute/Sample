package com.example.webapi_listpreview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;

public class MainActivity extends Activity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ウィンドウタイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Http.Request request = new Http.Request();
        request.url = "https://but-pazu-test.ssl-lolipop.jp/test_non2.php";

        Http.Response response = Http.requestSync(request, StringResponseHandler.getInstance());
        // HTTPステータスコードが200
        if (response.code == 200) {
            // 受信した文字列を取得
            String str = (String) response.value;

            ListView listView = (ListView) findViewById(R.id.listView1);

            Gson gson = new Gson();
            Sample[] sampleArray = gson.fromJson(str, Sample[].class);
            // リストに変換
            List<Sample> sampleList = Arrays.asList(sampleArray);

            List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
            for (Sample sampleRow : sampleList) {
                Map<String, String> data = new HashMap<String, String>();
                data.put("no", Common.getNo(sampleRow.no, sampleRow.rare));
                data.put("name", sampleRow.name);
                data.put("other2", "最大Lv." + sampleRow.lv);
                data.put("other3", "HP " + sampleRow.hp);
                data.put("other4", "攻撃 " + sampleRow.attack);
                data.put("other5", "回復 " + sampleRow.recovery);
                retDataList.add(data);
                //Log.d("Tag", "test3");
            }

            // リストビューに渡すアダプタを生成します。
            SimpleAdapter adapter2 = new SimpleAdapter(this, retDataList,
                    R.layout.raw, new String[] { "no", "name" ,"other2", "other3","other4","other5"},
                    new int[] {R.id.textView1, R.id.textView2 , R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7});

            // アダプタを設定します。
            listView.setAdapter(adapter2);
        }
    }
}

/*
* Json文字列をこのクラスに変化する
*/
class Sample{
    public String no;
    public String name;
    public String lv;
    public String hp;
    public String attack;
    public String recovery;
    public String rare;
}