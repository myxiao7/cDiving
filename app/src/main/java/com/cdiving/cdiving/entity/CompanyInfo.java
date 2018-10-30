package com.cdiving.cdiving.entity;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-10-27.
 */

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

    private String uid;
    private String login;
    private String login_salt;
    private String uname;
    private String email;
    private String sex;
    private String location;
    private String is_audit;
    private String is_active;
    private String is_init;
    private String ctime;
    private String identity;
    private String api_key;
    private String domain;
    private String province;
    private String city;
    private String area;
    private String reg_ip;
    private String lang;
    private String timezone;
    private String is_del;
    private String first_letter;
    private String intro;
    private String last_login_time;
    private String last_feed_id;
    private String last_post_time;
    private String search_key;
    private String invite_code;
    private String tel;
    private String website;
    private String address;
    private String latitude;
    private String longitude;
    private String is_service_provider;
    private String is_member;
    private String birthday;
    private String avatar_original;
    private String avatar_big;
    private String avatar_middle;
    private String avatar_small;
    private String avatar_tiny;
    private String avatar_url;
    private String space_url;
    private String space_link;
    private String space_link_no;
    private String group_icon;
    private FollowStateBean follow_state;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin_salt() {
        return login_salt;
    }

    public void setLogin_salt(String login_salt) {
        this.login_salt = login_salt;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIs_audit() {
        return is_audit;
    }

    public void setIs_audit(String is_audit) {
        this.is_audit = is_audit;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_init() {
        return is_init;
    }

    public void setIs_init(String is_init) {
        this.is_init = is_init;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReg_ip() {
        return reg_ip;
    }

    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getLast_feed_id() {
        return last_feed_id;
    }

    public void setLast_feed_id(String last_feed_id) {
        this.last_feed_id = last_feed_id;
    }

    public String getLast_post_time() {
        return last_post_time;
    }

    public void setLast_post_time(String last_post_time) {
        this.last_post_time = last_post_time;
    }

    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIs_service_provider() {
        return is_service_provider;
    }

    public void setIs_service_provider(String is_service_provider) {
        this.is_service_provider = is_service_provider;
    }

    public String getIs_member() {
        return is_member;
    }

    public void setIs_member(String is_member) {
        this.is_member = is_member;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar_original() {
        return avatar_original;
    }

    public void setAvatar_original(String avatar_original) {
        this.avatar_original = avatar_original;
    }

    public String getAvatar_big() {
        return avatar_big;
    }

    public void setAvatar_big(String avatar_big) {
        this.avatar_big = avatar_big;
    }

    public String getAvatar_middle() {
        return avatar_middle;
    }

    public void setAvatar_middle(String avatar_middle) {
        this.avatar_middle = avatar_middle;
    }

    public String getAvatar_small() {
        return avatar_small;
    }

    public void setAvatar_small(String avatar_small) {
        this.avatar_small = avatar_small;
    }

    public String getAvatar_tiny() {
        return avatar_tiny;
    }

    public void setAvatar_tiny(String avatar_tiny) {
        this.avatar_tiny = avatar_tiny;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getSpace_url() {
        return space_url;
    }

    public void setSpace_url(String space_url) {
        this.space_url = space_url;
    }

    public String getSpace_link() {
        return space_link;
    }

    public void setSpace_link(String space_link) {
        this.space_link = space_link;
    }

    public String getSpace_link_no() {
        return space_link_no;
    }

    public void setSpace_link_no(String space_link_no) {
        this.space_link_no = space_link_no;
    }

    public String getGroup_icon() {
        return group_icon;
    }

    public void setGroup_icon(String group_icon) {
        this.group_icon = group_icon;
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
