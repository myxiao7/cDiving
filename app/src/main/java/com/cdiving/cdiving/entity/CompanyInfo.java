package com.cdiving.cdiving.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author zhanghao
 * @date 2018-10-27.
 */
@Entity
public class CompanyInfo {


    /**
     * uid : 633
     * login : 494643819@qq.com
     * login_salt : 82645
     * uname : myxiao7
     * email : 494643819@qq.com
     * sex : 0
     * location : null
     * is_audit : 1
     * is_active : 1
     * is_init : 0
     * ctime : 1538982524
     * identity : 1
     * api_key : null
     * domain : null
     * province : 0
     * city : 0
     * area : 0
     * reg_ip : 127.0.0.1
     * lang : zh-cn
     * timezone : PRC
     * is_del : 0
     * first_letter : M
     * intro : null
     * last_login_time : 0
     * last_feed_id : 0
     * last_post_time : 0
     * search_key : myxiao7
     * invite_code : null
     * tel : null
     * website : null
     * address : null
     * latitude : 0
     * longitude : 0
     * is_service_provider : 0
     * is_member : 0
     * birthday : 1990-11-16
     * avatar_original : http://39.107.66.136/social/addons/theme/stv1/_static/image/noavatar/big.jpg
     * avatar_big : http://39.107.66.136/social/addons/theme/stv1/_static/image/noavatar/big.jpg
     * avatar_middle : http://39.107.66.136/social/addons/theme/stv1/_static/image/noavatar/middle.jpg
     * avatar_small : http://39.107.66.136/social/addons/theme/stv1/_static/image/noavatar/small.jpg
     * avatar_tiny : http://39.107.66.136/social/addons/theme/stv1/_static/image/noavatar/tiny.jpg
     * avatar_url : http://39.107.66.136/social/index.php?app=public&mod=Attach&act=avatar&uid=633
     * space_url : http://39.107.66.136/social/index.php?app=public&mod=Profile&act=index&uid=633
     * space_link : <a href='http://39.107.66.136/social/index.php?app=public&mod=Profile&act=index&uid=633' target='_blank' uid='633' event-node='face_card'>myxiao7</a>
     * space_link_no : <a href='http://39.107.66.136/social/index.php?app=public&mod=Profile&act=index&uid=633' title='myxiao7' target='_blank'>myxiao7</a>
     * medals : []
     * user_group : []
     * group_icon :
     * follow_state : {"following":0,"follower":0}
     * last_feed : []
     */

    @Id
    private Long ids;
    private String uid;
    @Transient
    private String login;
    @Transient
    private String login_salt;
    private String uname;
    private String email;
    @Transient
    private String sex;
    @Transient
    private String location;
    @Transient
    private String is_audit;
    @Transient
    private String is_active;
    @Transient
    private String is_init;
    @Transient
    private String ctime;
    @Transient
    private String identity;
    @Transient
    private String api_key;
    @Transient
    private String domain;
    @Transient
    private String province;
    @Transient
    private String city;
    @Transient
    private String area;
    @Transient
    private String reg_ip;
    @Transient
    private String lang;
    @Transient
    private String timezone;
    @Transient
    private String is_del;
    @Transient
    private String first_letter;
    @Transient
    private String intro;
    @Transient
    private String last_login_time;
    @Transient
    private String last_feed_id;
    @Transient
    private String last_post_time;
    @Transient
    private String search_key;
    @Transient
    private String invite_code;
    private String tel;
    private String website;
    private String address;
    private String latitude;
    private String longitude;
    private String is_service_provider;
    private int is_member;
    private int is_follow;
    @Transient
    private String birthday;
    @Transient
    private String avatar_original;
    @Transient
    private String avatar_big;
    private String avatar_middle;
    @Transient
    private String avatar_small;
    @Transient
    private String avatar_tiny;
    @Transient
    private String avatar_url;
    @Transient
    private String space_url;
    @Transient
    private String space_link;
    @Transient
    private String space_link_no;
    @Transient
    private String group_icon;
    @Transient
    private FollowStateBean follow_state;


    @Generated(hash = 610455292)
    public CompanyInfo(Long ids, String uid, String uname, String email, String tel, String website, String address, String latitude, String longitude,
            String is_service_provider, int is_member, int is_follow, String avatar_middle) {
        this.ids = ids;
        this.uid = uid;
        this.uname = uname;
        this.email = email;
        this.tel = tel;
        this.website = website;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.is_service_provider = is_service_provider;
        this.is_member = is_member;
        this.is_follow = is_follow;
        this.avatar_middle = avatar_middle;
    }


    @Generated(hash = 1062273323)
    public CompanyInfo() {
    }


    public Long getIds() {
        return this.ids;
    }


    public void setIds(Long ids) {
        this.ids = ids;
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


    public String getTel() {
        return this.tel;
    }


    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getWebsite() {
        return this.website;
    }


    public void setWebsite(String website) {
        this.website = website;
    }


    public String getAddress() {
        return this.address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getLatitude() {
        return this.latitude;
    }


    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getLongitude() {
        return this.longitude;
    }


    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public String getIs_service_provider() {
        return this.is_service_provider;
    }


    public void setIs_service_provider(String is_service_provider) {
        this.is_service_provider = is_service_provider;
    }


    public int getIs_member() {
        return this.is_member;
    }


    public void setIs_member(int is_member) {
        this.is_member = is_member;
    }


    public int getIs_follow() {
        return this.is_follow;
    }


    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }


    public String getAvatar_middle() {
        return this.avatar_middle;
    }


    public void setAvatar_middle(String avatar_middle) {
        this.avatar_middle = avatar_middle;
    }

    public FollowStateBean getFollow_state() {
        return follow_state;
    }

    public void setFollow_state(FollowStateBean follow_state) {
        this.follow_state = follow_state;
    }

    public static class FollowStateBean {
        /**
         * following : 0
         * follower : 0
         */

        private int following;
        private int follower;

        public int getFollowing() {
            return following;
        }

        public void setFollowing(int following) {
            this.following = following;
        }

        public int getFollower() {
            return follower;
        }

        public void setFollower(int follower) {
            this.follower = follower;
        }
    }
}
