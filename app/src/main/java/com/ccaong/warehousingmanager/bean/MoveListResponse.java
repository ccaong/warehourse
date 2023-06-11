package com.ccaong.warehousingmanager.bean;

import java.util.List;

public class MoveListResponse {


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
        private String orderType;
        private String orderNumber;
        private String orderMode;
        private String orderId;
        private String orderStatus;
        private String sourceWarehouseId;
        private String destinationWarehouseId;


        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderMode() {
            return orderMode;
        }

        public void setOrderMode(String orderMode) {
            this.orderMode = orderMode;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getSourceWarehouseId() {
            return sourceWarehouseId;
        }

        public void setSourceWarehouseId(String sourceWarehouseId) {
            this.sourceWarehouseId = sourceWarehouseId;
        }

        public String getDestinationWarehouseId() {
            return destinationWarehouseId;
        }

        public void setDestinationWarehouseId(String destinationWarehouseId) {
            this.destinationWarehouseId = destinationWarehouseId;
        }
    }
}
