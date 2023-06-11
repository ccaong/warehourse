package com.ccaong.warehousingmanager.bean;

import java.util.List;

/**
 * @author caocong
 * @date 2022/9/23
 */
public class InventoryDetailResponse {


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


    /**
     * 盘点任务详情
     */
    public static class DataDTO {
        private String orderNumber;
        private String createTime;
        private String storehouseId;
        private List<ListDTO> list;
        private String taskId;
        private String status;

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

        public String getStorehouseId() {
            return storehouseId;
        }

        public void setStorehouseId(String storehouseId) {
            this.storehouseId = storehouseId;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * 详情内某一条载具和状态
         */
        public static class ListDTO {
            private String containerCode;
            private List<ListDTOProduct> list;
            private String status;

            public String getContainerCode() {
                return containerCode;
            }

            public void setContainerCode(String containerCode) {
                this.containerCode = containerCode;
            }

            public List<ListDTOProduct> getList() {
                return list;
            }

            public void setList(List<ListDTOProduct> list) {
                this.list = list;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            /**
             * 某个载具内所有物料信息
             */
            public static class ListDTOProduct {
                private String id;
                private String locationCode;
                private String containerCode;
                private String materialCode;
                private String metarialName;
                private String skuName;
                private String specificationDesc;
                private String unit;
                private Integer amountIn;
                private Object inventoryAmount;
                private Object diff;
                private String status;
                private String pandianNum;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getLocationCode() {
                    return locationCode;
                }

                public void setLocationCode(String locationCode) {
                    this.locationCode = locationCode;
                }

                public String getContainerCode() {
                    return containerCode;
                }

                public void setContainerCode(String containerCode) {
                    this.containerCode = containerCode;
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

                public Integer getAmountIn() {
                    return amountIn;
                }

                public void setAmountIn(Integer amountIn) {
                    this.amountIn = amountIn;
                }

                public Object getInventoryAmount() {
                    return inventoryAmount;
                }

                public void setInventoryAmount(Object inventoryAmount) {
                    this.inventoryAmount = inventoryAmount;
                }

                public Object getDiff() {
                    return diff;
                }

                public void setDiff(Object diff) {
                    this.diff = diff;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getPandianNum() {
                    return pandianNum;
                }

                public void setPandianNum(String pandianNum) {
                    this.pandianNum = pandianNum;
                }
            }
        }
    }
}
