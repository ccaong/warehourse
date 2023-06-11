package com.ccaong.warehousingmanager.bean;

import java.util.List;

/**
 * 入库单详情数据
 *
 * @author caocong
 * @date 2022/9/20
 */
public class InboundDetailResponse {


    private String msg;
    private Integer code;
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private String createBy;
        private String createTime;
        private ParamsDTO params;
        private String id;
        private String source;
        private String type;
        private String badFlag;
        private String orderNumber;
        private String receiptId;
        private String delFlag;
        private List<GoodsTypeDetailDTO> goodsTypeDetail;
        private String storehouseId;
        private String storehouseCode;
        private String storehouseName;
        private String inboundType;
        private String customerName;
        private String orderStatus;

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

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBadFlag() {
            return badFlag;
        }

        public void setBadFlag(String badFlag) {
            this.badFlag = badFlag;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getReceiptId() {
            return receiptId;
        }

        public void setReceiptId(String receiptId) {
            this.receiptId = receiptId;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public List<GoodsTypeDetailDTO> getGoodsTypeDetail() {
            return goodsTypeDetail;
        }

        public void setGoodsTypeDetail(List<GoodsTypeDetailDTO> goodsTypeDetail) {
            this.goodsTypeDetail = goodsTypeDetail;
        }

        public String getStorehouseId() {
            return storehouseId;
        }

        public void setStorehouseId(String storehouseId) {
            this.storehouseId = storehouseId;
        }

        public String getStorehouseCode() {
            return storehouseCode;
        }

        public void setStorehouseCode(String storehouseCode) {
            this.storehouseCode = storehouseCode;
        }

        public String getStorehouseName() {
            return storehouseName;
        }

        public void setStorehouseName(String storehouseName) {
            this.storehouseName = storehouseName;
        }

        public String getInboundType() {
            return inboundType;
        }

        public void setInboundType(String inboundType) {
            this.inboundType = inboundType;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public static class ParamsDTO {
        }

        public static class GoodsTypeDetailDTO {
            private String materialCode;
            private String metarialName;
            private String goodsTypeId;
            private String skuName;
            private String specificationDesc;
            private String unit;
            private String volume;
            private String weight;
            private Integer amount;
            private Integer quantity;
            private String supportInformation;
            private String serialNumber;

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }

            @Override
            public String toString() {
                return metarialName+skuName;
            }

            public String getGoodsTypeId() {
                return goodsTypeId;
            }

            public void setGoodsTypeId(String goodsTypeId) {
                this.goodsTypeId = goodsTypeId;
            }

            public String getMaterialCode() {
                return materialCode;
            }

            public void setMaterialCode(String materialCode) {
                this.materialCode = materialCode;
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

            public Integer getAmount() {
                return amount;
            }

            public void setAmount(Integer amount) {
                this.amount = amount;
            }


            public String getSupportInformation() {
                return supportInformation;
            }

            public void setSupportInformation(String supportInformation) {
                this.supportInformation = supportInformation;
            }

            public String getSerialNumber() {
                return serialNumber;
            }

            public void setSerialNumber(String serialNumber) {
                this.serialNumber = serialNumber;
            }
        }
    }
}
