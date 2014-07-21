package com.example.webapi_listpreview;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.example.webapi_listpreview.Http.Response;

import android.graphics.BitmapFactory;

public abstract class BitmapResponseHandler extends Http.ResponseHandlerBase {

    // 受信データをオブジェクトに変換
    @Override
    public Object createObjectFromResponse(HttpResponse response) {
        byte[] bin;
        try {
            bin = EntityUtils.toByteArray(response.getEntity());
        } catch (IOException e) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        return BitmapFactory.decodeByteArray(bin, 0, bin.length, options);
    }

    // 空のインスタンスを返す
    public static final BitmapResponseHandler getInstance() {
        return new BitmapResponseHandler() {
            @Override
            public void onFinish(Response response) {
            }
        };
    }

}