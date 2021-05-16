package com.sun.myapplication1.utils;

import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    public static void sendOkHttpRequest (String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder() .url(address) . build() ;
        client.newCall(request).enqueue(callback);
    }

    public static void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user","admin")
                            .build();
                    Request request = new Request.Builder()
                            .url("http://192.168.24.203:8080/getData")                           //该服务器的api（URL）
                            .addHeader("userkey", "55656535336494b84c749b31453ea55")
                            .addHeader("content-type", "application/json;charset:utf-8")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    Log.d("MainActivity","sendOK");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
