package com.cdiving.cdiving.http.api;

import com.cdiving.cdiving.entity.CompanyInfo;
import com.cdiving.cdiving.entity.CompanyResult;
import com.cdiving.cdiving.entity.RegisterStatus;
import com.cdiving.cdiving.entity.RongYunToken;
import com.cdiving.cdiving.entity.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author zhanghao
 * @date 2018-10-19.
 */

public interface UserApi {
    /**
     * 密码登陆
     * @param uid 账号
     * @param password 密码
     * @param act authorize_new
     * @param app api
     * @param mod Oauth
     * @return
     */
    @FormUrlEncoded
    @POST("social/index.php?")
    Observable<UserInfo> login(@Field("act") String act, @Field("app") String app, @Field("mod") String mod,
                               @Field("uid") String uid, @Field("passwd") String password);

    /**
     * 注册
     * @param email 邮箱
     * @param uName 账号
     * @param password 密码
     * @param act register_email
     * @param app api
     * @param mod Oauth
     * @return
     */
    @FormUrlEncoded
    @POST("social/index.php?")
    Observable<RegisterStatus> register(@Field("act") String act, @Field("app") String app, @Field("mod") String mod,
                                        @Field("email") String email, @Field("uname") String uName, @Field("password") String password);

    /**
     * 融云token
     * @param act getIMToken_debug
     * @param app api
     * @param mod Oauth
     * @param oauthToken Oauth
     * @param secret secret
     * @param userId id
     * @param userName name
     * @return
     */
    @FormUrlEncoded
    @POST("social/index.php?")
    Observable<RongYunToken> getRongYunToken(@Field("act") String act, @Field("app") String app, @Field("mod") String mod,
                                             @Field("oauth_token") String oauthToken, @Field("oauth_token_secret") String secret,
                                             @Field("userName") String userName, @Field("userId") String userId);

    /**
     * 获取个人信息（包括商家信息）
     * @param act register_email
     * @param app api
     * @param mod Oauth
     * @param oauthToken Oauth
     * @param secret secret
     * @param userId id
     * @return
     */
    @FormUrlEncoded
    @POST("social/index.php?")
    Observable<CompanyInfo> getUserInfo(@Field("act") String act, @Field("app") String app, @Field("mod") String mod,
                                        @Field("oauth_token") String oauthToken, @Field("oauth_token_secret") String secret,
                                        @Field("user_id") String userId);


    /**
     * 获取首页公司信息
     * @return
     */
    @GET("public/api/serviceProviderList")
    Observable<CompanyResult> getCompanyAddress();
}


