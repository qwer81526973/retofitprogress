package com.example.administrator.myapplication22;



import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/11.
 */

public interface api {
    @GET("{id}")
    Observable<ResponseBody> getpic(@Path("id") String id);
}
