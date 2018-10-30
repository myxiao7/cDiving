package com.cdiving.cdiving.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pgyersdk.crash.PgyCrashManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author zhanghao
 * @date 2018-10-18.
 */

public class BaseActivity extends RxAppCompatActivity {
    protected BaseActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        PgyCrashManager.register(this);
    }
}
