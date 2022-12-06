package com.ccaong.warehousingmanager.bean;

/**
 * @author eyecool
 * @date 2022/9/19
 */
public class SpinnerTestBean {
    private String code;

    private String name;

    public SpinnerTestBean(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
