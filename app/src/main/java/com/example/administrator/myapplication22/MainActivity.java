package com.example.administrator.myapplication22;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;



public class MainActivity extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView)findViewById(R.id.imageView);
        //Glide.with(this).load("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-04-18299181_1306649979420798_1108869403736276992_n.jpg").error(R.mipmap.ic_launcher).into(img);

        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                int progress = (int) ((bytesRead * 100) / contentLength);
                Log.i("LB",""+progress);
            }
        });

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();



        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("http://ww1.sinaimg.cn/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api a = retrofit.create(api.class);
        a.getpic("large/61e74233ly1feuogwvg27j20p00zkqe7.jpg")

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody responseBody) {
                byte[] data=new byte[10];
                try {
                    data = responseBody.bytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap factory = BitmapFactory.decodeByteArray(data,0,data.length);
                img.setImageDrawable(new BitmapDrawable(factory));
            }
        });

    }
}
