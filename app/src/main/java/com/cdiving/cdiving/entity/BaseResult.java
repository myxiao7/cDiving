package com.cdiving.cdiving.entity;

/**
 * @author zhanghao
 * @date 2018-10-22.
 */

public class BaseResult {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
