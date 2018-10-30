package com.cdiving.cdiving.utils.db;

import com.cdiving.cdiving.base.BaseApplication;
import com.cdiving.cdiving.entity.DaoSession;
import com.cdiving.cdiving.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghao
 * @date 2018-10-22.
 */

public class DbUtil {
   private static DaoSession daoSession = BaseApplication.getApplication().getDaoSession();

    /**
     * 创建新用户
     */
    public static void createEmptyUser(){
        daoSession.deleteAll(UserInfo.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setIsLogin(false);
        daoSession.insert(userInfo);
    }

    /**
     * 保存用户信息
     * @param userInfo
     */
    public static void updateUserInfo(UserInfo userInfo){
        UserInfo info = getUserInfo();
        if(info == null){
            return;
        }
        info.setUid(userInfo.getUid());
        info.setUname(userInfo.getUname());
        info.setOauth_token(userInfo.getOauth_token());
        info.setOauth_token_secret(userInfo.getOauth_token_secret());
        info.setIsLogin(userInfo.getIsLogin());

        daoSession.getUserInfoDao().update(info);
    }


    /**
     * 获取用户信息
     * @return
     */
    public static UserInfo getUserInfo(){
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos = daoSession.getUserInfoDao().loadAll();
        if(userInfos.size()>0){
            return userInfos.get(0);
        }
        return null;
    }
}
