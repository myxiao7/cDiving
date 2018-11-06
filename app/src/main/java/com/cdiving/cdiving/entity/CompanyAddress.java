package com.cdiving.cdiving.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author zhanghao
 * @date 2018-11-05.
 */
@Entity
public class CompanyAddress {
    @Id
    private Long ids;
    private String uid;
    private String address;
    private String latitude;
    private String longitude;
    @Generated(hash = 1409230193)
    public CompanyAddress(Long ids, String uid, String address, String latitude,
            String longitude) {
        this.ids = ids;
        this.uid = uid;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Generated(hash = 1449494674)
    public CompanyAddress() {
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
    
}
