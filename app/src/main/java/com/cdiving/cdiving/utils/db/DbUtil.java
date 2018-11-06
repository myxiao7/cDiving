package com.cdiving.cdiving.utils.db;

import com.cdiving.cdiving.base.BaseApplication;
import com.cdiving.cdiving.entity.CompanyAddress;
import com.cdiving.cdiving.entity.CompanyAddressDao;
import com.cdiving.cdiving.entity.CompanyInfo;
import com.cdiving.cdiving.entity.CompanyInfoDao;
import com.cdiving.cdiving.entity.CompanyResult;
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
     * 更新用户信息
     * @param userInfo
     */
    public static void updateUserInfo(UserInfo userInfo){
        UserInfo info = getUserInfo();
        if(info == null){
            return;
        }
        info.setUid(userInfo.getUid());
        info.setUname(userInfo.getUname());
        info.setEmail(userInfo.getEmail());
        info.setPortrait(userInfo.getPortrait());
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

    /**
     * 缓存首页公司列表
     * @param companyAddresses
     */
    public static void insertCompanyList(final List<CompanyAddress> companyAddresses){
        daoSession.startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                daoSession.deleteAll(CompanyAddress.class);
                for (CompanyAddress address:companyAddresses
                        ) {
                    daoSession.getCompanyAddressDao().insert(address);
                }
            }
        });
    }

    /**
     * 获取首页公司列表
     */
    public static List<CompanyAddress> getcompanyAddresses(){
        return daoSession.getCompanyAddressDao().queryBuilder().orderAsc(CompanyAddressDao.Properties.Ids).build().list();
    }

    /**
     * 新建或更新公司信息
     * @param companyInfo
     */
    public static void UpdateCompanyInfo(CompanyInfo companyInfo, String userId){
        CompanyInfo info = getCompanyInfo(userId);
        if(info == null){
            daoSession.insert(companyInfo);
        }else{
            info.setUid(companyInfo.getUid());
            info.setUname(companyInfo.getUname());
            info.setEmail(companyInfo.getEmail());
            info.setTel(companyInfo.getTel());
            info.setWebsite(companyInfo.getWebsite());
            info.setAvatar_middle(companyInfo.getAvatar_middle());
            info.setLang(companyInfo.getLang());
            info.setLatitude(companyInfo.getLatitude());
            info.setIs_service_provider(companyInfo.getIs_service_provider());
            info.setIs_member(companyInfo.getIs_member());
            daoSession.getCompanyInfoDao().update(info);
        }
    }

    /**
     * 获取公司信息
     * @return
     */
    public static CompanyInfo getCompanyInfo(String userId){
        List<CompanyInfo> companyInfos = new ArrayList<>();
        companyInfos = daoSession.getCompanyInfoDao().queryBuilder().where(CompanyInfoDao.Properties.Uid.eq(userId)).list();
        if(companyInfos.size()>0){
            return companyInfos.get(0);
        }
        return null;
    }
}
