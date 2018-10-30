package com.cdiving.cdiving.utils;

import android.widget.Toast;

import com.cdiving.cdiving.base.BaseApplication;


/**
 * Toast 工具类
 * @author zhanghao
 * @date 2018-08-23.
 */


public class ToastUtil {
    static Toast toast;
    static BaseApplication application = BaseApplication.getApplication();
    /**
     * 短时间显示toast
     * @param msg
     */
    public static void showShort(String msg){
        if(toast == null){
            toast = Toast.makeText(application, msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 短时间显示toast
     * @param msgId 资源文件
     */
    public static void showShort(int msgId){
        String msg = application.getResources().getString(msgId);
        if(toast == null){
            toast = Toast.makeText(application, msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 长时间显示toast
     * @param msg
     */
    public static void showLong(String msg){
        if(toast == null){
            toast = Toast.makeText(application, msg, Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 长时间显示toast
     * @param msgId 资源文件
     */
    public static void showLong(int msgId){
        String msg = application.getResources().getString(msgId);
        if(toast == null){
            toast = Toast.makeText(application, msg, Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 隐藏Toast
     */
    public void hide(){
        if(toast !=null){
            toast.cancel();
        }
    }
}
