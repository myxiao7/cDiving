package com.cdiving.cdiving.entity;

import java.util.List;

/**
 * 首页公司列列表
 * @author zhanghao
 * @date 2018-10-27.
 */

public class CompanyResult {

    /**
     * status : 1
     * version : 1
     * results : [{"uid":194,"address":"辽宁省大连市西岗区北海街19号203室","latitude":38.932220458984,"longitude":121.63600158691},{"uid":193,"address":"秦皇岛市经济技术开发区盛景金领域5-1301号","latitude":39.937564849854,"longitude":119.53263092041}]
     */

    private int status;
    private int version;
    private List<CompanyAddress> results;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<CompanyAddress> getResults() {
        return results;
    }

    public void setResults(List<CompanyAddress> results) {
        this.results = results;
    }

}
