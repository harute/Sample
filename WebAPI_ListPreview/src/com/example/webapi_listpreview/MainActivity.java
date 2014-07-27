package com.example.webapi_listpreview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;

public class MainActivity extends Activity implements TextWatcher {

    private Map<String, String> data;
    private EditText mEditText; // 変更を検知するエディットボックス
    List<Sample> baseSampleList;
    List<Map<String, String>> baseRetDataList;
    ListView listView;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ウィンドウタイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // リスナーを仕込むエディットボックス
        this.mEditText = (EditText)this.findViewById(R.id.editText1);
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // EditTextのフォーカスが外れた場合
                if (hasFocus == false) {
                 // ソフトキーボードを非表示にする
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        this.mEditText.addTextChangedListener(this);

        Http.Request request = new Http.Request();
        request.url = "https://but-pazu-test.ssl-lolipop.jp/test_non2.php";

        Http.Response response = Http.requestSync(request, StringResponseHandler.getInstance());
        // HTTPステータスコードが200
        if (response.code == 200) {
            // 受信した文字列を取得
            String str = (String) response.value;

            listView = (ListView) findViewById(R.id.listView1);

            Gson gson = new Gson();
            Sample[] sampleArray = gson.fromJson(str, Sample[].class);
            // リストに変換
            List<Sample> sampleList = Arrays.asList(sampleArray);
            //コピー
            baseSampleList = new ArrayList<Sample>(sampleList);

            List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
            for (Sample sampleRow : sampleList) {
                data = new HashMap<String, String>();
                data.put("no", Common.getNo(sampleRow.no, sampleRow.rare));
                data.put("name", sampleRow.name);
                data.put("other2", "最大Lv." + sampleRow.lv);
                data.put("other3", "HP " + sampleRow.hp);
                data.put("other4", "攻撃 " + sampleRow.attack);
                data.put("other5", "回復 " + sampleRow.recovery);
                retDataList.add(data);
                //Log.d("Tag", "test3");
            }
            baseRetDataList = retDataList;

            // リストビューに渡すアダプタを生成します。
            SimpleAdapter adapter2 = new SimpleAdapter(this, retDataList,
                    R.layout.raw, new String[] { "no", "name" ,"other2", "other3","other4","other5"},
                    new int[] {R.id.textView1, R.id.textView2 , R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7});

            // アダプタを設定します。
            listView.setAdapter(adapter2);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO 自動生成されたメソッド・スタブ
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO 2回呼ばれる
        List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
        if (s.toString() != "") {
            // ここでテキスト変更後の処理
            for (Sample sampleRow : baseSampleList) {
                //Log.d("tag", "base=" + s.toString());
                if (sampleRow.name.indexOf(s.toString()) > -1) {
                    data = new HashMap<String, String>();
                    data.put("no", Common.getNo(sampleRow.no, sampleRow.rare));
                    data.put("name", sampleRow.name);
                    data.put("other2", "最大Lv." + sampleRow.lv);
                    data.put("other3", "HP " + sampleRow.hp);
                    data.put("other4", "攻撃 " + sampleRow.attack);
                    data.put("other5", "回復 " + sampleRow.recovery);
                    //Log.d("tag", "result=" + sampleRow.name);
                    retDataList.add(data);
                }
            }


            // リストビューに渡すアダプタを生成します。
            SimpleAdapter adapter2 = new SimpleAdapter(this, retDataList,
                    R.layout.raw, new String[] { "no", "name" ,"other2", "other3","other4","other5"},
                    new int[] {R.id.textView1, R.id.textView2 , R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7});

            // アダプタを設定します。
            listView.setAdapter(adapter2);
        } else {
            // リストビューに渡すアダプタを生成します。
            SimpleAdapter adapter2 = new SimpleAdapter(this, baseRetDataList,
                    R.layout.raw, new String[] { "no", "name" ,"other2", "other3","other4","other5"},
                    new int[] {R.id.textView1, R.id.textView2 , R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7});

            // アダプタを設定します。
            listView.setAdapter(adapter2);
        }
    }
}

/*
* Json文字列をこのクラスに変化するa
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