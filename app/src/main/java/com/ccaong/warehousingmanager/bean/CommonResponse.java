package com.ccaong.warehousingmanager.bean;

/**
 * @author caocong
 * @date 2022/9/28
 */
public class CommonResponse {


    private String msg;
    private Integer code;
    private Integer data;

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

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}
