package com.ccaong.warehousingmanager.bean;

import java.util.List;

/**
 * @author eyecool
 * @date 2022/10/14
 */
public class SortListResponse {


    private Integer total;
    private List<RowsDTO> rows;
    private Integer code;
    private String msg;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class RowsDTO {
        private String orderNumber;
        private String relNumber;
        private List<RelsDTO> rels;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getRelNumber() {
            return relNumber;
        }

        public void setRelNumber(String relNumber) {
            this.relNumber = relNumber;
        }

        public List<RelsDTO> getRels() {
            return rels;
        }

        public void setRels(List<RelsDTO> rels) {
            this.rels = rels;
        }

        public static class RelsDTO {
            private String createBy;
            private String createTime;
            private ParamsDTO params;
            private String id;
            private String orderOutboundId;
            private String goodsInfoId;
            private Integer goodsCount;
            private String storageLocationId;
            private Integer goodsRestCount;
            private String goodsTypeId;
            private GoodsInfoDTO goodsInfo;
            private OrderOutboundDTO orderOutbound;
            private String pickStatus;
            private String relNumber;

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

            public String getOrderOutboundId() {
                return orderOutboundId;
            }

            public void setOrderOutboundId(String orderOutboundId) {
                this.orderOutboundId = orderOutboundId;
            }

            public String getGoodsInfoId() {
                return goodsInfoId;
            }

            public void setGoodsInfoId(String goodsInfoId) {
                this.goodsInfoId = goodsInfoId;
            }

            public Integer getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(Integer goodsCount) {
                this.goodsCount = goodsCount;
            }

            public String getStorageLocationId() {
                return storageLocationId;
            }

            public void setStorageLocationId(String storageLocationId) {
                this.storageLocationId = storageLocationId;
            }

            public Integer getGoodsRestCount() {
                return goodsRestCount;
            }

            public void setGoodsRestCount(Integer goodsRestCount) {
                this.goodsRestCount = goodsRestCount;
            }

            public String getGoodsTypeId() {
                return goodsTypeId;
            }

            public void setGoodsTypeId(String goodsTypeId) {
                this.goodsTypeId = goodsTypeId;
            }

            public GoodsInfoDTO getGoodsInfo() {
                return goodsInfo;
            }

            public void setGoodsInfo(GoodsInfoDTO goodsInfo) {
                this.goodsInfo = goodsInfo;
            }

            public OrderOutboundDTO getOrderOutbound() {
                return orderOutbound;
            }

            public void setOrderOutbound(OrderOutboundDTO orderOutbound) {
                this.orderOutbound = orderOutbound;
            }

            public String getPickStatus() {
                return pickStatus;
            }

            public void setPickStatus(String pickStatus) {
                this.pickStatus = pickStatus;
            }

            public String getRelNumber() {
                return relNumber;
            }

            public void setRelNumber(String relNumber) {
                this.relNumber = relNumber;
            }

            public static class ParamsDTO {
            }

            public static class GoodsInfoDTO {
                private ParamsDTO params;
                private String id;
                private Integer goodsAmount;
                private String materialCode;
                private String locationCode;
                private String goodsTypeId;
                private String typeId;
                private String storageLocationId;
                private Integer realAmount;
                private String containerSerialNum;
                private GoodsTypeDTO goodsType;

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

                public Integer getGoodsAmount() {
                    return goodsAmount;
                }

                public void setGoodsAmount(Integer goodsAmount) {
                    this.goodsAmount = goodsAmount;
                }

                public String getMaterialCode() {
                    return materialCode;
                }

                public void setMaterialCode(String materialCode) {
                    this.materialCode = materialCode;
                }

                public String getLocationCode() {
                    return locationCode;
                }

                public void setLocationCode(String locationCode) {
                    this.locationCode = locationCode;
                }

                public String getGoodsTypeId() {
                    return goodsTypeId;
                }

                public void setGoodsTypeId(String goodsTypeId) {
                    this.goodsTypeId = goodsTypeId;
                }

                public String getTypeId() {
                    return typeId;
                }

                public void setTypeId(String typeId) {
                    this.typeId = typeId;
                }

                public String getStorageLocationId() {
                    return storageLocationId;
                }

                public void setStorageLocationId(String storageLocationId) {
                    this.storageLocationId = storageLocationId;
                }

                public Integer getRealAmount() {
                    return realAmount;
                }

                public void setRealAmount(Integer realAmount) {
                    this.realAmount = realAmount;
                }

                public String getContainerSerialNum() {
                    return containerSerialNum;
                }

                public void setContainerSerialNum(String containerSerialNum) {
                    this.containerSerialNum = containerSerialNum;
                }

                public GoodsTypeDTO getGoodsType() {
                    return goodsType;
                }

                public void setGoodsType(GoodsTypeDTO goodsType) {
                    this.goodsType = goodsType;
                }

                public static class ParamsDTO {
                }

                public static class GoodsTypeDTO {
                    private ParamsDTO params;
                    private String id;
                    private String parentId;
                    private String name;
                    private String goodsUnit;
                    private String skuCode;
                    private String specificationDesc;
                    private String volume;
                    private List<?> children;

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

                    public String getGoodsUnit() {
                        return goodsUnit;
                    }

                    public void setGoodsUnit(String goodsUnit) {
                        this.goodsUnit = goodsUnit;
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

                    public List<?> getChildren() {
                        return children;
                    }

                    public void setChildren(List<?> children) {
                        this.children = children;
                    }

                    public static class ParamsDTO {
                    }
                }
            }

            public static class OrderOutboundDTO {
                private ParamsDTO params;
                private String id;
                private String orderNumber;
                private String badFlag;
                private Boolean isDone;
                private String outboundType;

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

                public String getOrderNumber() {
                    return orderNumber;
                }

                public void setOrderNumber(String orderNumber) {
                    this.orderNumber = orderNumber;
                }

                public String getBadFlag() {
                    return badFlag;
                }

                public void setBadFlag(String badFlag) {
                    this.badFlag = badFlag;
                }

                public Boolean getIsDone() {
                    return isDone;
                }

                public void setIsDone(Boolean isDone) {
                    this.isDone = isDone;
                }

                public String getOutboundType() {
                    return outboundType;
                }

                public void setOutboundType(String outboundType) {
                    this.outboundType = outboundType;
                }

                public static class ParamsDTO {
                }
            }
        }
    }
}
