package com.cdiving.cdiving.http;

import android.content.Intent;

import com.cdiving.cdiving.R;
import com.cdiving.cdiving.base.BaseApplication;
import com.cdiving.cdiving.login.LoginActivity;
import com.cdiving.cdiving.utils.ToastUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * 处理Token过期
 * @author zhanghao
 * @date 2018-08-23.
 */

public class TokenAuthenticator implements Authenticator {
    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        ToastUtil.showShort(R.string.login_status_invalid);
                        //清除用户信息
                        Intent intent = new Intent(BaseApplication.getApplication(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        BaseApplication.getApplication().startActivity(intent);
                    }
                });
        return null;
    }
}
