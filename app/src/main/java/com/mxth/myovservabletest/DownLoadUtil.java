package com.mxth.myovservabletest;

import android.os.SystemClock;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/1/16.
 */

public class DownLoadUtil {

    private  static OkHttpClient client;
    private  static  String path="http://h.hiphotos.baidu.com/zhidao/pic/item/6d81800a19d8bc3ed69473cb848ba61ea8d34516.jpg";
    public DownLoadUtil(){
        client=new OkHttpClient();
    }
    public  Observable downLoadImg(){
        return Observable.create(new Observable.OnSubscribe<byte[]>(){

            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                Request request=new Request.Builder().url(path).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            byte[] bytes = response.body().bytes();
                            if(bytes!=null){
                                SystemClock.sleep(2000);
                             subscriber.onNext(bytes);
                            }
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }
}
