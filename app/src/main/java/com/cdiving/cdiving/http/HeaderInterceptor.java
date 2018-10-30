package com.cdiving.cdiving.http;

import android.text.TextUtils;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * token拦截器
 * @author zhanghao
 * @date 2018-08-23.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
       /* //添加Authorization
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if(DbUtils.getUserInfo() != null){
            if(!TextUtils.isEmpty(DbUtils.getUserInfo().getToken())){
                builder.addHeader("Authorization", DbUtils.getUserInfo().getToken());
            }
        }

        //获取Authorization
        Response response = chain.proceed(builder.build());
        String token = response.header("Authorization");
        if(DbUtils.getUserInfo() != null && !TextUtils.isEmpty(token)){
            DbUtils.updateAuth(token);
        }
        return response;*/
        return null;
    }
}
