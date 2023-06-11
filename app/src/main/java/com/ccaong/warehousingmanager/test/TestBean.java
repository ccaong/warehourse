package com.ccaong.warehousingmanager.test;

public class TestBean {

    private String sku;

    private String code;

    public TestBean(String sku, String code) {
        this.sku = sku;
        this.code = code;
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
}
