package com.ccaong.warehousingmanager.bean;

/**
 * @author eyecool
 * @date 2022/10/10
 */
public class InOrderNumberResponse {


    private String msg;
    private Integer code;
    private String data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
