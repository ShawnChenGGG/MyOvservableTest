package com.mxth.myovservabletest;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private DownLoadUtil util;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        util = new DownLoadUtil();
        pd = new ProgressDialog(this);
        pd.setMessage("玩儿命加载中...");
    }

    public void downLoad(View v) {
        pd.show();
        util.downLoadImg().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<byte[]>() {
            @Override
            public void onCompleted() {
                if (pd != null) {
                    pd.dismiss();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("message", e.toString());
                if (pd != null) {
                    pd.dismiss();
                }
            }

            @Override
            public void onNext(byte[] o) {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(o, 0, o.length));
            }
        });
    }
}
