package com.cdiving.cdiving.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cdiving.cdiving.R;
import com.cdiving.cdiving.base.BaseActivity;

/**
 * 个人中心
 * @author zhanghao
 * @date 2018-11-01.
 */

public class UserCenter extends BaseActivity {

    public static void start(Context context){
        Intent intent = new Intent(context, UserCenter.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
    }
}
