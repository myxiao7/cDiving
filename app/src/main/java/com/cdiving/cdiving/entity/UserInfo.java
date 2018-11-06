package com.cdiving.cdiving.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * 用户信息
 * @author zhanghao
 * @date 2018-08-23.
 */
@Entity
public class UserInfo {
    @Id
    private Long ids;
    private int code;
    private String message;

    private String uid;
    private String uname;
    private String email;
    private String portrait;

    private String oauth_token;

    private String oauth_token_secret;
    private boolean isLogin;
    @Generated(hash = 33299582)
    public UserInfo(Long ids, int code, String message, String uid, String uname,
            String email, String portrait, String oauth_token,
            String oauth_token_secret, boolean isLogin) {
        this.ids = ids;
        this.code = code;
        this.message = message;
        this.uid = uid;
        this.uname = uname;
        this.email = email;
        this.portrait = portrait;
        this.oauth_token = oauth_token;
        this.oauth_token_secret = oauth_token_secret;
        this.isLogin = isLogin;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public Long getIds() {
        return this.ids;
    }
    public void setIds(Long ids) {
        this.ids = ids;
    }
    public int getCode() {
        return this.code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUname() {
        return this.uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPortrait() {
        return this.portrait;
    }
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
    public String getOauth_token() {
        return this.oauth_token;
    }
    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }
    public String getOauth_token_secret() {
        return this.oauth_token_secret;
    }
    public void setOauth_token_secret(String oauth_token_secret) {
        this.oauth_token_secret = oauth_token_secret;
    }
    public boolean getIsLogin() {
        return this.isLogin;
    }
    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
   
}
