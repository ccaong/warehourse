package com.ccaong.warehousingmanager.bean;

public class ChildGoodBean {

    private String sku;

    private String code;

    private String serialNo;



    public ChildGoodBean(String sku, String code, String no) {
        this.sku = sku;
        this.code = code;
        this.serialNo = no;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
