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
    private List<ResultsBean> results;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * uname :   滨州市沾化区昊岳水下工程有限公司
         * tel : +86 15224389042
         * address : 山东省滨州市沾化区冯家镇庄子村
         * email : 1187895794@qq.com
         * webSite :
         * is_member : 0
         */

        private String uname;
        private String tel;
        private String address;
        private String email;
        private String webSite;
        private int is_member;

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWebSite() {
            return webSite;
        }

        public void setWebSite(String webSite) {
            this.webSite = webSite;
        }

        public int getIs_member() {
            return is_member;
        }

        public void setIs_member(int is_member) {
            this.is_member = is_member;
        }
    }
}
