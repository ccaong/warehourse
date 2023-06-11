package com.ccaong.warehousingmanager.bean;

import java.util.List;

/**
 * 载具移库，物资移库
 */
public class MoveLibDetailResponse {


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
        private String orderType;
        private String createBy;
        private String orderNumber;
        private String createTime;
        private List<DatasDTO> datas;
        private String sourceWarehouse;
        private String destinationWarehouse;

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<DatasDTO> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasDTO> datas) {
            this.datas = datas;
        }

        public String getSourceWarehouse() {
            return sourceWarehouse;
        }

        public void setSourceWarehouse(String sourceWarehouse) {
            this.sourceWarehouse = sourceWarehouse;
        }

        public String getDestinationWarehouse() {
            return destinationWarehouse;
        }

        public void setDestinationWarehouse(String destinationWarehouse) {
            this.destinationWarehouse = destinationWarehouse;
        }

        public static class DatasDTO {
            private String containerId;
            private Integer goodsAmount;
            private GoodsInfoDTO goodsInfo;
            private String goodsInfoId;
            private String id;
            private String inboundOrderNumber;
            private String orderId;
            private ParamsDTO params;
            private String srcLocId;
            private SrcLocationDTO srcLocation;
            private String status;
            private String typeId;

            public String getContainerId() {
                return containerId;
            }

            public void setContainerId(String containerId) {
                this.containerId = containerId;
            }

            public Integer getGoodsAmount() {
                return goodsAmount;
            }

            public void setGoodsAmount(Integer goodsAmount) {
                this.goodsAmount = goodsAmount;
            }

            public GoodsInfoDTO getGoodsInfo() {
                return goodsInfo;
            }

            public void setGoodsInfo(GoodsInfoDTO goodsInfo) {
                this.goodsInfo = goodsInfo;
            }

            public String getGoodsInfoId() {
                return goodsInfoId;
            }

            public void setGoodsInfoId(String goodsInfoId) {
                this.goodsInfoId = goodsInfoId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getInboundOrderNumber() {
                return inboundOrderNumber;
            }

            public void setInboundOrderNumber(String inboundOrderNumber) {
                this.inboundOrderNumber = inboundOrderNumber;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public ParamsDTO getParams() {
                return params;
            }

            public void setParams(ParamsDTO params) {
                this.params = params;
            }

            public String getSrcLocId() {
                return srcLocId;
            }

            public void setSrcLocId(String srcLocId) {
                this.srcLocId = srcLocId;
            }

            public SrcLocationDTO getSrcLocation() {
                return srcLocation;
            }

            public void setSrcLocation(SrcLocationDTO srcLocation) {
                this.srcLocation = srcLocation;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTypeId() {
                return typeId;
            }

            public void setTypeId(String typeId) {
                this.typeId = typeId;
            }

            public static class GoodsInfoDTO {
                private String badFlag;
                private ContainerDTO container;
                private String containerId;
                private String containerSerialNum;
                private String createTime;
                private String delFlag;
                private Integer goodsAmount;
                private GoodsTypeDTO goodsType;
                private String goodsTypeId;
                private String goodsTypeName;
                private String id;
                private String indate;
                private String locationCode;
                private String manufacturerId;
                private String materialCode;
                private String orderNum;
                private String orderTime;
                private String ownershipUnitId;
                private ParamsDTO params;
                private Integer realAmount;
                private String status;
                private StorageLocationDTO storageLocation;
                private String storageLocationId;
                private String storehouseId;
                private Integer thenAmount;
                private Long typeId;

                public String getBadFlag() {
                    return badFlag;
                }

                public void setBadFlag(String badFlag) {
                    this.badFlag = badFlag;
                }

                public ContainerDTO getContainer() {
                    return container;
                }

                public void setContainer(ContainerDTO container) {
                    this.container = container;
                }

                public String getContainerId() {
                    return containerId;
                }

                public void setContainerId(String containerId) {
                    this.containerId = containerId;
                }

                public String getContainerSerialNum() {
                    return containerSerialNum;
                }

                public void setContainerSerialNum(String containerSerialNum) {
                    this.containerSerialNum = containerSerialNum;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getDelFlag() {
                    return delFlag;
                }

                public void setDelFlag(String delFlag) {
                    this.delFlag = delFlag;
                }

                public Integer getGoodsAmount() {
                    return goodsAmount;
                }

                public void setGoodsAmount(Integer goodsAmount) {
                    this.goodsAmount = goodsAmount;
                }

                public GoodsTypeDTO getGoodsType() {
                    return goodsType;
                }

                public void setGoodsType(GoodsTypeDTO goodsType) {
                    this.goodsType = goodsType;
                }

                public String getGoodsTypeId() {
                    return goodsTypeId;
                }

                public void setGoodsTypeId(String goodsTypeId) {
                    this.goodsTypeId = goodsTypeId;
                }

                public String getGoodsTypeName() {
                    return goodsTypeName;
                }

                public void setGoodsTypeName(String goodsTypeName) {
                    this.goodsTypeName = goodsTypeName;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getIndate() {
                    return indate;
                }

                public void setIndate(String indate) {
                    this.indate = indate;
                }

                public String getLocationCode() {
                    return locationCode;
                }

                public void setLocationCode(String locationCode) {
                    this.locationCode = locationCode;
                }

                public String getManufacturerId() {
                    return manufacturerId;
                }

                public void setManufacturerId(String manufacturerId) {
                    this.manufacturerId = manufacturerId;
                }

                public String getMaterialCode() {
                    return materialCode;
                }

                public void setMaterialCode(String materialCode) {
                    this.materialCode = materialCode;
                }

                public String getOrderNum() {
                    return orderNum;
                }

                public void setOrderNum(String orderNum) {
                    this.orderNum = orderNum;
                }

                public String getOrderTime() {
                    return orderTime;
                }

                public void setOrderTime(String orderTime) {
                    this.orderTime = orderTime;
                }

                public String getOwnershipUnitId() {
                    return ownershipUnitId;
                }

                public void setOwnershipUnitId(String ownershipUnitId) {
                    this.ownershipUnitId = ownershipUnitId;
                }

                public ParamsDTO getParams() {
                    return params;
                }

                public void setParams(ParamsDTO params) {
                    this.params = params;
                }

                public Integer getRealAmount() {
                    return realAmount;
                }

                public void setRealAmount(Integer realAmount) {
                    this.realAmount = realAmount;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public StorageLocationDTO getStorageLocation() {
                    return storageLocation;
                }

                public void setStorageLocation(StorageLocationDTO storageLocation) {
                    this.storageLocation = storageLocation;
                }

                public String getStorageLocationId() {
                    return storageLocationId;
                }

                public void setStorageLocationId(String storageLocationId) {
                    this.storageLocationId = storageLocationId;
                }

                public String getStorehouseId() {
                    return storehouseId;
                }

                public void setStorehouseId(String storehouseId) {
                    this.storehouseId = storehouseId;
                }

                public Integer getThenAmount() {
                    return thenAmount;
                }

                public void setThenAmount(Integer thenAmount) {
                    this.thenAmount = thenAmount;
                }

                public Long getTypeId() {
                    return typeId;
                }

                public void setTypeId(Long typeId) {
                    this.typeId = typeId;
                }

                public static class ContainerDTO {
                    private String barCode;
                    private String delFlag;
                    private String id;
                    private ParamsDTO params;
                    private String serialNum;
                    private String status;
                    private String typeId;
                    private String used;

                    public String getBarCode() {
                        return barCode;
                    }

                    public void setBarCode(String barCode) {
                        this.barCode = barCode;
                    }

                    public String getDelFlag() {
                        return delFlag;
                    }

                    public void setDelFlag(String delFlag) {
                        this.delFlag = delFlag;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public ParamsDTO getParams() {
                        return params;
                    }

                    public void setParams(ParamsDTO params) {
                        this.params = params;
                    }

                    public String getSerialNum() {
                        return serialNum;
                    }

                    public void setSerialNum(String serialNum) {
                        this.serialNum = serialNum;
                    }

                    public String getStatus() {
                        return status;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }

                    public String getTypeId() {
                        return typeId;
                    }

                    public void setTypeId(String typeId) {
                        this.typeId = typeId;
                    }

                    public String getUsed() {
                        return used;
                    }

                    public void setUsed(String used) {
                        this.used = used;
                    }

                    public static class ParamsDTO {
                    }
                }

                public static class GoodsTypeDTO {
                    private List<?> children;
                    private String id;
                    private String name;
                    private ParamsDTO params;

                    public List<?> getChildren() {
                        return children;
                    }

                    public void setChildren(List<?> children) {
                        this.children = children;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public ParamsDTO getParams() {
                        return params;
                    }

                    public void setParams(ParamsDTO params) {
                        this.params = params;
                    }

                    public static class ParamsDTO {
                    }
                }

                public static class ParamsDTO {
                }

                public static class StorageLocationDTO {
                    private String areaId;
                    private Integer floor;
                    private String id;
                    private String locationStatus;
                    private String name;
                    private ParamsDTO params;
                    private String roadwayId;
                    private Integer storageLocationNum;
                    private StorageRoadwayDTO storageRoadway;
                    private StorehouseAreaDTO storehouseArea;
                    private String storehouseId;
                    private StorehouseInfoDTO storehouseInfo;

                    public String getAreaId() {
                        return areaId;
                    }

                    public void setAreaId(String areaId) {
                        this.areaId = areaId;
                    }

                    public Integer getFloor() {
                        return floor;
                    }

                    public void setFloor(Integer floor) {
                        this.floor = floor;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getLocationStatus() {
                        return locationStatus;
                    }

                    public void setLocationStatus(String locationStatus) {
                        this.locationStatus = locationStatus;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public ParamsDTO getParams() {
                        return params;
                    }

                    public void setParams(ParamsDTO params) {
                        this.params = params;
                    }

                    public String getRoadwayId() {
                        return roadwayId;
                    }

                    public void setRoadwayId(String roadwayId) {
                        this.roadwayId = roadwayId;
                    }

                    public Integer getStorageLocationNum() {
                        return storageLocationNum;
                    }

                    public void setStorageLocationNum(Integer storageLocationNum) {
                        this.storageLocationNum = storageLocationNum;
                    }

                    public StorageRoadwayDTO getStorageRoadway() {
                        return storageRoadway;
                    }

                    public void setStorageRoadway(StorageRoadwayDTO storageRoadway) {
                        this.storageRoadway = storageRoadway;
                    }

                    public StorehouseAreaDTO getStorehouseArea() {
                        return storehouseArea;
                    }

                    public void setStorehouseArea(StorehouseAreaDTO storehouseArea) {
                        this.storehouseArea = storehouseArea;
                    }

                    public String getStorehouseId() {
                        return storehouseId;
                    }

                    public void setStorehouseId(String storehouseId) {
                        this.storehouseId = storehouseId;
                    }

                    public StorehouseInfoDTO getStorehouseInfo() {
                        return storehouseInfo;
                    }

                    public void setStorehouseInfo(StorehouseInfoDTO storehouseInfo) {
                        this.storehouseInfo = storehouseInfo;
                    }

                    public static class ParamsDTO {
                    }

                    public static class StorageRoadwayDTO {
                        private String areaId;
                        private Integer floor;
                        private String id;
                        private Integer maxRow;
                        private String name;
                        private ParamsDTO params;
                        private Integer serialNum;
                        private String storehouseId;

                        public String getAreaId() {
                            return areaId;
                        }

                        public void setAreaId(String areaId) {
                            this.areaId = areaId;
                        }

                        public Integer getFloor() {
                            return floor;
                        }

                        public void setFloor(Integer floor) {
                            this.floor = floor;
                        }

                        public String getId() {
                            return id;
                        }

                        public void setId(String id) {
                            this.id = id;
                        }

                        public Integer getMaxRow() {
                            return maxRow;
                        }

                        public void setMaxRow(Integer maxRow) {
                            this.maxRow = maxRow;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public ParamsDTO getParams() {
                            return params;
                        }

                        public void setParams(ParamsDTO params) {
                            this.params = params;
                        }

                        public Integer getSerialNum() {
                            return serialNum;
                        }

                        public void setSerialNum(Integer serialNum) {
                            this.serialNum = serialNum;
                        }

                        public String getStorehouseId() {
                            return storehouseId;
                        }

                        public void setStorehouseId(String storehouseId) {
                            this.storehouseId = storehouseId;
                        }

                        public static class ParamsDTO {
                        }
                    }

                    public static class StorehouseAreaDTO {
                        private String areaType;
                        private String id;
                        private String name;
                        private ParamsDTO params;
                        private String serialNum;
                        private String skuType;
                        private String storageRule;
                        private String storehouseId;

                        public String getAreaType() {
                            return areaType;
                        }

                        public void setAreaType(String areaType) {
                            this.areaType = areaType;
                        }

                        public String getId() {
                            return id;
                        }

                        public void setId(String id) {
                            this.id = id;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public ParamsDTO getParams() {
                            return params;
                        }

                        public void setParams(ParamsDTO params) {
                            this.params = params;
                        }

                        public String getSerialNum() {
                            return serialNum;
                        }

                        public void setSerialNum(String serialNum) {
                            this.serialNum = serialNum;
                        }

                        public String getSkuType() {
                            return skuType;
                        }

                        public void setSkuType(String skuType) {
                            this.skuType = skuType;
                        }

                        public String getStorageRule() {
                            return storageRule;
                        }

                        public void setStorageRule(String storageRule) {
                            this.storageRule = storageRule;
                        }

                        public String getStorehouseId() {
                            return storehouseId;
                        }

                        public void setStorehouseId(String storehouseId) {
                            this.storehouseId = storehouseId;
                        }

                        public static class ParamsDTO {
                        }
                    }

                    public static class StorehouseInfoDTO {
                        private String id;
                        private String name;
                        private ParamsDTO params;
                        private String serialNum;

                        public String getId() {
                            return id;
                        }

                        public void setId(String id) {
                            this.id = id;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public ParamsDTO getParams() {
                            return params;
                        }

                        public void setParams(ParamsDTO params) {
                            this.params = params;
                        }

                        public String getSerialNum() {
                            return serialNum;
                        }

                        public void setSerialNum(String serialNum) {
                            this.serialNum = serialNum;
                        }

                        public static class ParamsDTO {
                        }
                    }
                }
            }

            public static class ParamsDTO {
            }

            public static class SrcLocationDTO {
                private String id;
                private String name;
                private ParamsDTO params;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public ParamsDTO getParams() {
                    return params;
                }

                public void setParams(ParamsDTO params) {
                    this.params = params;
                }

                public static class ParamsDTO {
                }
            }
        }
    }
}
