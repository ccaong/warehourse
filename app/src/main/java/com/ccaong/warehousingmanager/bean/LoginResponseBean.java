package com.ccaong.warehousingmanager.bean;

/**
 * @author eyecool
 * @date 2022/9/20
 */
public class LoginResponseBean {


    private String msg;
    private Integer code;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
