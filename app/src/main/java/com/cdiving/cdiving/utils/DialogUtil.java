package com.cdiving.cdiving.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cdiving.cdiving.R;


/**
 * Dialog 工具类
 * @author zhanghao
 * @date 2018-08-23.
 */

public class DialogUtil {
    private static MaterialDialog dialog;

    /**
     * 显示对话框
     * @param context
     * @param res
     */
    public static void showProgress(Context context, int res){
        if(!(dialog!= null && dialog.isShowing())){
            dialog = new MaterialDialog.Builder(context)
                    .progress(true, 0)
                    .content(res)
                    .progressIndeterminateStyle(false)
                    .cancelable(false)
                    .show();
        }else {
            dialog.dismiss();
        }
    }

    /**
     * 显示对话框
     * @param context
     */
    public static void showProgress(Context context){
        if(!(dialog!= null && dialog.isShowing())){
            dialog = new MaterialDialog.Builder(context)
                    .progress(true, 0)
                    .content(R.string.please_wait)
                    .content(R.string.please_wait)
                    .progressIndeterminateStyle(false)
                    .cancelable(true)
                    .show();
        }else {
            dialog.dismiss();
        }
    }

    /**
     * 关闭对话框
     * @param context
     */
    public static void stopProgress(Context context) {
        if ((dialog != null && dialog.isShowing())) {
            dialog.dismiss();
        }
    }
}
