package com.ccaong.warehousingmanager.bean;

import java.util.List;

/**
 * @author eyecool
 * @date 2022/9/29
 */
public class GoodsTypeListResponse {


    private String msg;
    private Integer code;
    private List<DataDTO> data;

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

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private ParamsDTO params;
        private String id;
        private String parentId;
        private String name;
        private String status;
        private String delFlag;
        private String pinyin;
        private Boolean isCombination;
        private String goodsUnit;
        private Integer typeNumber;
        private String isSKU;
        private String materialCode;
        private String skuCode;
        private String specificationDesc;
        private String volume;
        private Integer shelfLife;
        private Double weight;
        private String auxiliaryUnitJson;
        private String hasSKU;
        private List<?> children;
        private String ancestors;

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public ParamsDTO getParams() {
            return params;
        }

        public void setParams(ParamsDTO params) {
            this.params = params;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public Boolean getIsCombination() {
            return isCombination;
        }

        public void setIsCombination(Boolean isCombination) {
            this.isCombination = isCombination;
        }

        public String getGoodsUnit() {
            return goodsUnit;
        }

        public void setGoodsUnit(String goodsUnit) {
            this.goodsUnit = goodsUnit;
        }

        public Integer getTypeNumber() {
            return typeNumber;
        }

        public void setTypeNumber(Integer typeNumber) {
            this.typeNumber = typeNumber;
        }

        public String getIsSKU() {
            return isSKU;
        }

        public void setIsSKU(String isSKU) {
            this.isSKU = isSKU;
        }

        public String getMaterialCode() {
            return materialCode;
        }

        public void setMaterialCode(String materialCode) {
            this.materialCode = materialCode;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public String getSpecificationDesc() {
            return specificationDesc;
        }

        public void setSpecificationDesc(String specificationDesc) {
            this.specificationDesc = specificationDesc;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public Integer getShelfLife() {
            return shelfLife;
        }

        public void setShelfLife(Integer shelfLife) {
            this.shelfLife = shelfLife;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public String getAuxiliaryUnitJson() {
            return auxiliaryUnitJson;
        }

        public void setAuxiliaryUnitJson(String auxiliaryUnitJson) {
            this.auxiliaryUnitJson = auxiliaryUnitJson;
        }

        public String getHasSKU() {
            return hasSKU;
        }

        public void setHasSKU(String hasSKU) {
            this.hasSKU = hasSKU;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }

        public String getAncestors() {
            return ancestors;
        }

        public void setAncestors(String ancestors) {
            this.ancestors = ancestors;
        }

        public static class ParamsDTO {
        }
    }
}
