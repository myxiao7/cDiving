package com.cdiving.cdiving.utils.db;

import com.cdiving.cdiving.base.BaseApplication;
import com.cdiving.cdiving.entity.CompanyAddress;
import com.cdiving.cdiving.entity.CompanyAddressDao;
import com.cdiving.cdiving.entity.CompanyDetail;
import com.cdiving.cdiving.entity.CompanyDetailDao;
import com.cdiving.cdiving.entity.CompanyInfo;
import com.cdiving.cdiving.entity.CompanyInfoDao;
import com.cdiving.cdiving.entity.CompanyResult;
import com.cdiving.cdiving.entity.DaoSession;
import com.cdiving.cdiving.entity.Follow;
import com.cdiving.cdiving.entity.FollowDao;
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
    public static List<CompanyAddress> getCompanyAddresses(){
        return daoSession.getCompanyAddressDao().queryBuilder().orderAsc(CompanyAddressDao.Properties.Ids).build().list();
    }

    /**
     * 新建或更新公司详情(未登录)
     * @param companyDetail
     */
    public static void UpdateCompanyDetail(CompanyDetail companyDetail, String userId){
        CompanyDetail detail = getCompanyDetail(userId);
        if(detail == null){
            companyDetail.setUid(userId);
            daoSession.getCompanyDetailDao().insert(companyDetail);
        }else{
            detail.setUid(userId);
            detail.setUname(companyDetail.getUname());
            detail.setEmail(companyDetail.getEmail());
            detail.setTel(companyDetail.getTel());
            detail.setWebSite(companyDetail.getWebSite());
            detail.setAddress(companyDetail.getAddress());
            detail.setIs_member(companyDetail.getIs_member());
            daoSession.getCompanyDetailDao().update(detail);
        }
    }

    /**
     * 获取公司详情(未登录)
     * @return
     */
    public static CompanyDetail getCompanyDetail(String userId){
        List<CompanyDetail> companyDetails = new ArrayList<>();
        companyDetails = daoSession.getCompanyDetailDao().queryBuilder().where(CompanyDetailDao.Properties.Uid.eq(userId)).list();
        if(companyDetails.size()>0){
            return companyDetails.get(0);
        }
        return null;
    }

    /**
     * 新建或更新公司详情(登录)
     * @param companyInfo
     */
    public static void UpdateCompanyDetail2(CompanyInfo companyInfo, String userId){
        CompanyInfo info = getCompanyDetail2(userId);
        if(info == null){
            companyInfo.setUid(userId);
            daoSession.getCompanyInfoDao().insert(companyInfo);
        }else{
            info.setUid(userId);
            info.setUname(companyInfo.getUname());
            info.setEmail(companyInfo.getEmail());
            info.setTel(companyInfo.getTel());
            info.setWebsite(companyInfo.getWebsite());
            info.setAddress(companyInfo.getAddress());
            info.setIs_member(companyInfo.getIs_member());
            info.setIs_follow(companyInfo.getIs_follow());
            daoSession.getCompanyInfoDao().update(info);
        }
    }

    /**
     * 获取公司详情(登录)
     * @return
     */
    public static CompanyInfo getCompanyDetail2(String userId){
        List<CompanyInfo> companyInfos = new ArrayList<>();
        companyInfos = daoSession.getCompanyInfoDao().queryBuilder().where(CompanyInfoDao.Properties.Uid.eq(userId)).list();
        if(companyInfos.size()>0){
            return companyInfos.get(0);
        }
        return null;
    }


    /**
     * 缓存关注列表
     * @param follows
     */
    public static void insertFollowList(final List<Follow> follows){
        daoSession.startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                daoSession.deleteAll(Follow.class);
                for (Follow follow:follows
                        ) {
                    daoSession.getFollowDao().insert(follow);
                }
            }
        });
    }

    /**
     * 获取关注列表
     */
    public static List<Follow> getFollowList(){
        return daoSession.getFollowDao().queryBuilder().orderAsc(FollowDao.Properties.Ids).build().list();
    }
}
