package com.cdiving.cdiving.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author zhanghao
 * @date 2018-11-09.
 */
@Entity
public class Follow {
    @Id
    private Long ids;
    private String uid;
    private String uname;
    private String avatar_middle;

    @Generated(hash = 863552889)
    public Follow(Long ids, String uid, String uname, String avatar_middle) {
        this.ids = ids;
        this.uid = uid;
        this.uname = uname;
        this.avatar_middle = avatar_middle;
    }

    @Generated(hash = 2125231264)
    public Follow() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAvatar_middle() {
        return avatar_middle;
    }

    public void setAvatar_middle(String avatar_middle) {
        this.avatar_middle = avatar_middle;
    }

    public Long getIds() {
        return this.ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }
}
