package com.ccaong.warehousingmanager.bean;

/**
 * @author caocong
 * @date 2022/9/28
 */
public class GroupGoodBean {

    private String zjId;
    private String materialCode;
    private String metarialName;
    private String skuName;
    private String specificationDesc;
    private String unit;
    private String volume;
    private String weight;
    private Integer needAmount;

    private Integer receivedAmount;


    private String goodsTypeId;
    private String goodsTypeCode;
    private String goodsUnit;


    private Integer quantity;
    private String supportInformation;

    // 序列号
    private String serialNumber;


    public GroupGoodBean() {
    }

    public GroupGoodBean(String materialCode, Integer needAmount, Integer receivedAmount) {
        this.materialCode = materialCode;
        this.needAmount = needAmount;
        this.receivedAmount = receivedAmount;
    }

    public GroupGoodBean(String materialCode, Integer needAmount, Integer receivedAmount, Integer quantity) {
        this.materialCode = materialCode;
        this.needAmount = needAmount;
        this.receivedAmount = receivedAmount;
        this.quantity = quantity;
    }


    public String getZjId() {
        return zjId;
    }

    public void setZjId(String zjId) {
        this.zjId = zjId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
        this.goodsTypeCode = materialCode;
    }

    public String getMetarialName() {
        return metarialName;
    }

    public void setMetarialName(String metarialName) {
        this.metarialName = metarialName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSpecificationDesc() {
        return specificationDesc;
    }

    public void setSpecificationDesc(String specificationDesc) {
        this.specificationDesc = specificationDesc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        this.goodsUnit = unit;

    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(Integer needAmount) {
        this.needAmount = needAmount;
    }

    public Integer getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(Integer receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }

    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getSupportInformation() {
        return supportInformation;
    }

    public void setSupportInformation(String supportInformation) {
        this.supportInformation = supportInformation;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
