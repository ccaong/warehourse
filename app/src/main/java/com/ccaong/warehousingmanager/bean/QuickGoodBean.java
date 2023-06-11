package com.ccaong.warehousingmanager.bean;

import java.util.Date;

/**
 * @author caocong
 * @date 2022/9/29
 */
public class QuickGoodBean {

    /**
     * 货物类型id
     */
    private String goodsTypeId;
    /**
     * 货物编码
     */
    private String goodsTypeCode;
    /**
     * 接收数量
     */
    private int receivedAmount;
    /**
     * 储位id，
     */
    private String locationId;
    /**
     * 储位编码
     */
    private String locationCode;

    /**
     * 货物单位
     */
    private String goodsUnit;


    /**
     * 生产日期
     */

    private String manufacturerDate;

    /**
     * 生产厂家id
     */
    private String manufacturerId;
    private String manufacturerName;


    /**
     * 品级
     */
    private String goodsGrade;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 序列号
     */
    private String serialNumber;

    /**
     * 配套信息
     */
    private String supportInformation;


    /**
     * 备注
     */
    private String remark;


    /**
     * 应收数量
     */

    /**
     * 库中数量
     */


    /**
     * 下列的四个属性为列表展示中用，接口不需要
     */
    private String goodsName;

    private String Sku;

    private String zj;

    private String wz;


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

    public int getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(int receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }

    public String getWz() {
        return wz;
    }

    public void setWz(String wz) {
        this.wz = wz;
    }

    public String getManufacturerDate() {
        return manufacturerDate;
    }

    public void setManufacturerDate(String manufacturerDate) {
        this.manufacturerDate = manufacturerDate;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getGoodsGrade() {
        return goodsGrade;
    }

    public void setGoodsGrade(String goodsGrade) {
        this.goodsGrade = goodsGrade;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSupportInformation() {
        return supportInformation;
    }

    public void setSupportInformation(String supportInformation) {
        this.supportInformation = supportInformation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
