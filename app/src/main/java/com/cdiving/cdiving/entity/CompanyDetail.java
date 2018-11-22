package com.cdiving.cdiving.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author zhanghao
 * @date 2018-11-06.
 */
@Entity
public class CompanyDetail {
    /**
     * uname :   滨州市沾化区昊岳水下工程有限公司
     * tel : +86 15224389042
     * address : 山东省滨州市沾化区冯家镇庄子村
     * email : 1187895794@qq.com
     * webSite :
     * is_member : 0
     */
    @Id
    private Long ids;
    private String uid;
    private String uname;
    private String tel;
    private String address;
    private String email;
    private String webSite;
    private int is_member;
    @Generated(hash = 1205936309)
    public CompanyDetail(Long ids, String uid, String uname, String tel,
            String address, String email, String webSite, int is_member) {
        this.ids = ids;
        this.uid = uid;
        this.uname = uname;
        this.tel = tel;
        this.address = address;
        this.email = email;
        this.webSite = webSite;
        this.is_member = is_member;
    }
    @Generated(hash = 1741909527)
    public CompanyDetail() {
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
    public String getTel() {
        return this.tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getWebSite() {
        return this.webSite;
    }
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    public int getIs_member() {
        return this.is_member;
    }
    public void setIs_member(int is_member) {
        this.is_member = is_member;
    }

    
}
