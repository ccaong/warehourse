package com.ccaong.warehousingmanager.bean;

import java.util.List;

public class MoveVehicleDetailResponse {


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
            private String destinationLocationCode;
            private String id;
            private String sourceLocationCode;

            public String getDestinationLocationCode() {
                return destinationLocationCode;
            }

            public void setDestinationLocationCode(String destinationLocationCode) {
                this.destinationLocationCode = destinationLocationCode;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSourceLocationCode() {
                return sourceLocationCode;
            }

            public void setSourceLocationCode(String sourceLocationCode) {
                this.sourceLocationCode = sourceLocationCode;
            }
        }
    }
}
