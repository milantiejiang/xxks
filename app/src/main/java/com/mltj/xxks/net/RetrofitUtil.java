package com.mltj.xxks.net;

import android.content.Context;

import com.mltj.xxks.util.Contents;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author 米兰铁匠
 * Created by 米兰铁匠 on 2016/11/4.
 */
public class RetrofitUtil {
    public static Retrofit getRetrofitInstance(Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
//                        .header("User-Agent", "com.cn.hzyy.yuanyi")
//                        .header("token", "com.cn.hzyy.yuanyi1234567890")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        //设置五秒钟连接超时
        httpClient.connectTimeout(5, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contents.getBaseUrl(context))
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
