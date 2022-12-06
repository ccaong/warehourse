package com.ccaong.warehousingmanager.bean;

import java.util.List;

/**
 * @author eyecool
 * @date 2022/11/23
 */
public class SortWorkResponse {


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
        private String updateTime;
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
        private StorageLocationDTO storageLocation;
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

        public StorageLocationDTO getStorageLocation() {
            return storageLocation;
        }

        public void setStorageLocation(StorageLocationDTO storageLocation) {
            this.storageLocation = storageLocation;
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
            private String goodsTypeName;
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

            public String getGoodsTypeName() {
                return goodsTypeName;
            }

            public void setGoodsTypeName(String goodsTypeName) {
                this.goodsTypeName = goodsTypeName;
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
                private List<?> children;
                private String parentName;

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

                public List<?> getChildren() {
                    return children;
                }

                public void setChildren(List<?> children) {
                    this.children = children;
                }

                public String getParentName() {
                    return parentName;
                }

                public void setParentName(String parentName) {
                    this.parentName = parentName;
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

        public static class StorageLocationDTO {
            private String createBy;
            private String createTime;
            private String updateTime;
            private ParamsDTO params;
            private String id;
            private String storehouseName;
            private String areaName;
            private String roadwayName;
            private String name;
            private String storehouseId;
            private String roadwayId;
            private String areaId;
            private StorehouseAreaDTO storehouseArea;
            private StorageRoadwayDTO storageRoadway;
            private StorehouseInfoDTO storehouseInfo;
            private Integer floor;
            private Integer storageLocationNum;
            private String locationStatus;
            private String status;
            private String delFlag;
            private String containerId;
            private Integer coordinatex;
            private Integer coordinatey;
            private Double floorHeight;

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

            public String getStorehouseName() {
                return storehouseName;
            }

            public void setStorehouseName(String storehouseName) {
                this.storehouseName = storehouseName;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public String getRoadwayName() {
                return roadwayName;
            }

            public void setRoadwayName(String roadwayName) {
                this.roadwayName = roadwayName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStorehouseId() {
                return storehouseId;
            }

            public void setStorehouseId(String storehouseId) {
                this.storehouseId = storehouseId;
            }

            public String getRoadwayId() {
                return roadwayId;
            }

            public void setRoadwayId(String roadwayId) {
                this.roadwayId = roadwayId;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public StorehouseAreaDTO getStorehouseArea() {
                return storehouseArea;
            }

            public void setStorehouseArea(StorehouseAreaDTO storehouseArea) {
                this.storehouseArea = storehouseArea;
            }

            public StorageRoadwayDTO getStorageRoadway() {
                return storageRoadway;
            }

            public void setStorageRoadway(StorageRoadwayDTO storageRoadway) {
                this.storageRoadway = storageRoadway;
            }

            public StorehouseInfoDTO getStorehouseInfo() {
                return storehouseInfo;
            }

            public void setStorehouseInfo(StorehouseInfoDTO storehouseInfo) {
                this.storehouseInfo = storehouseInfo;
            }

            public Integer getFloor() {
                return floor;
            }

            public void setFloor(Integer floor) {
                this.floor = floor;
            }

            public Integer getStorageLocationNum() {
                return storageLocationNum;
            }

            public void setStorageLocationNum(Integer storageLocationNum) {
                this.storageLocationNum = storageLocationNum;
            }

            public String getLocationStatus() {
                return locationStatus;
            }

            public void setLocationStatus(String locationStatus) {
                this.locationStatus = locationStatus;
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

            public String getContainerId() {
                return containerId;
            }

            public void setContainerId(String containerId) {
                this.containerId = containerId;
            }

            public Integer getCoordinatex() {
                return coordinatex;
            }

            public void setCoordinatex(Integer coordinatex) {
                this.coordinatex = coordinatex;
            }

            public Integer getCoordinatey() {
                return coordinatey;
            }

            public void setCoordinatey(Integer coordinatey) {
                this.coordinatey = coordinatey;
            }

            public Double getFloorHeight() {
                return floorHeight;
            }

            public void setFloorHeight(Double floorHeight) {
                this.floorHeight = floorHeight;
            }

            public static class ParamsDTO {
            }

            public static class StorehouseAreaDTO {
                private String createBy;
                private String createTime;
                private String updateTime;
                private ParamsDTO params;
                private String id;
                private String storehouseId;
                private String name;
                private String serialNum;
                private String cargoDistributeType;
                private String status;
                private String delFlag;

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

                public String getStorehouseId() {
                    return storehouseId;
                }

                public void setStorehouseId(String storehouseId) {
                    this.storehouseId = storehouseId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSerialNum() {
                    return serialNum;
                }

                public void setSerialNum(String serialNum) {
                    this.serialNum = serialNum;
                }

                public String getCargoDistributeType() {
                    return cargoDistributeType;
                }

                public void setCargoDistributeType(String cargoDistributeType) {
                    this.cargoDistributeType = cargoDistributeType;
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

                public static class ParamsDTO {
                }
            }

            public static class StorageRoadwayDTO {
                private String createBy;
                private String createTime;
                private String updateTime;
                private ParamsDTO params;
                private String id;
                private String storehouseId;
                private String areaId;
                private String storehouseName;
                private String areaName;
                private String name;
                private Integer serialNum;
                private Integer floor;
                private Integer maxRow;
                private String status;
                private String delFlag;

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

                public String getStorehouseId() {
                    return storehouseId;
                }

                public void setStorehouseId(String storehouseId) {
                    this.storehouseId = storehouseId;
                }

                public String getAreaId() {
                    return areaId;
                }

                public void setAreaId(String areaId) {
                    this.areaId = areaId;
                }

                public String getStorehouseName() {
                    return storehouseName;
                }

                public void setStorehouseName(String storehouseName) {
                    this.storehouseName = storehouseName;
                }

                public String getAreaName() {
                    return areaName;
                }

                public void setAreaName(String areaName) {
                    this.areaName = areaName;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Integer getSerialNum() {
                    return serialNum;
                }

                public void setSerialNum(Integer serialNum) {
                    this.serialNum = serialNum;
                }

                public Integer getFloor() {
                    return floor;
                }

                public void setFloor(Integer floor) {
                    this.floor = floor;
                }

                public Integer getMaxRow() {
                    return maxRow;
                }

                public void setMaxRow(Integer maxRow) {
                    this.maxRow = maxRow;
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

                public static class ParamsDTO {
                }
            }

            public static class StorehouseInfoDTO {
                private String createBy;
                private String createTime;
                private String updateTime;
                private ParamsDTO params;
                private String id;
                private String name;
                private String serialNum;
                private String delFlag;
                private String status;

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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSerialNum() {
                    return serialNum;
                }

                public void setSerialNum(String serialNum) {
                    this.serialNum = serialNum;
                }

                public String getDelFlag() {
                    return delFlag;
                }

                public void setDelFlag(String delFlag) {
                    this.delFlag = delFlag;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public static class ParamsDTO {
                }
            }
        }
    }
}
