package com.cdiving.cdiving.entity;

import java.util.List;

/**
 * 首页公司信息
 * @author zhanghao
 * @date 2018-10-31.
 */

public class CompanyIndexInfo {

    /**
     * status : 1
     * results : [{"uname":"  滨州市沾化区昊岳水下工程有限公司","tel":"+86 15224389042","address":"山东省滨州市沾化区冯家镇庄子村","email":"1187895794@qq.com","webSite":"","is_member":0}]
     */

    private int status;
    private List<CompanyDetail> results;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CompanyDetail> getResults() {
        return results;
    }

    public void setResults(List<CompanyDetail> results) {
        this.results = results;
    }

}
