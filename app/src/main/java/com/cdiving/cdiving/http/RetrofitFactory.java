package com.cdiving.cdiving.http;

import com.cdiving.cdiving.BuildConfig;
import com.cdiving.cdiving.base.Config;
import com.cdiving.cdiving.http.api.UserApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 工具
 * @author zhanghao
 * @date 2018-10-19.
 */

public class RetrofitFactory {
    private static Retrofit getRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttp());
        return builder.build();
    }

    private static OkHttpClient getOkHttp(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //拦截器
//        builder.addInterceptor(new HeaderInterceptor());
        //token过期
        builder.authenticator(new TokenAuthenticator());
        //打印日志
        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

    public static UserApi getUserApi(){
        return getRetrofit().create(UserApi.class);
    }
}
