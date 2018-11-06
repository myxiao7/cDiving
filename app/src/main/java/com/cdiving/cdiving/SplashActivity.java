package com.cdiving.cdiving;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;


import com.cdiving.cdiving.base.BaseActivity;
import com.cdiving.cdiving.utils.BrandUtils;
import com.cdiving.cdiving.utils.SPUtils;
import com.cdiving.cdiving.utils.db.DbUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * 入口
 * @author zhanghao
 * @date 2018-09-05.
 */

public class SplashActivity extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final String INSTALL_FLAG = "isFirstInstall";
    private RxPermissions rxPermissions;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        if (DbUtil.getUserInfo() == null) {
            DbUtil.createEmptyUser();
        }
        final long startTime = System.currentTimeMillis();
        rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission
                .WRITE_EXTERNAL_STORAGE, Manifest
                .permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            long timeLeft = System.currentTimeMillis() - startTime;
                            if (timeLeft > 0 && timeLeft < 500) {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        gotoMain();
                                    }
                                }, 500 - timeLeft);
                            }else{
                                gotoMain();
                            }
                        } else {
                            StringBuilder sb = new StringBuilder();
                            if (!rxPermissions.isGranted(Manifest.permission
                                    .WRITE_EXTERNAL_STORAGE)) {
                                sb.append(getString(R.string.permission_storage));
                                sb.append("、");
                            }
                            if (!rxPermissions.isGranted(Manifest.permission
                                    .ACCESS_COARSE_LOCATION)) {
                                sb.append(getString(R.string.permission_location));
                                sb.append("、");
                            }
                            if (!rxPermissions.isGranted(Manifest.permission
                                    .READ_PHONE_STATE)) {
                                sb.append(getString(R.string.permission_phone));
                                sb.append("、");
                            }
                            sb.delete(sb.length() - 1, sb.length());
                            showPermissionsDialog(getString(R.string.permission_need, sb.toString()));
                        }
                    }
                });
    }

    private void gotoMain(){
        if(SPUtils.getInstance().getBoolean(INSTALL_FLAG,true)){
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    private void showPermissionsDialog(String title) {
        new AlertDialog.Builder(this).setMessage(title).setNegativeButton(R.string.cancel, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BrandUtils.startPermissionActivity(SplashActivity.this, REQUEST_CODE_PERMISSION);
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (!rxPermissions.isGranted(Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                finish();
                return;
            }
            if (!rxPermissions.isGranted(Manifest.permission
                    .ACCESS_COARSE_LOCATION)) {
                finish();
                return;
            }
            if (!rxPermissions.isGranted(Manifest.permission
                    .READ_PHONE_STATE)) {
                finish();
                return;
            }
            startActivity(new Intent(activity, SplashActivity.class));
            finish();
        }
    }
}
