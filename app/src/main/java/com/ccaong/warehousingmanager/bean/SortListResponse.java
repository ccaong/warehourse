package com.ccaong.warehousingmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author caocong
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

    public static class RowsDTO implements Serializable {
        private String orderNumber;
        private List<ListDTO> list;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class ListDTO  implements Serializable {
            private String orderNumber;
            private String relNumber;
            // outbound：出库拣货，typeHou：物资移库；locHou：载具移库； locCont：载具移位
            private String relSource;
            private List<RelListDTO> relList;

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

            public String getRelSource() {
                return relSource;
            }

            public void setRelSource(String relSource) {
                this.relSource = relSource;
            }

            public List<RelListDTO> getRelList() {
                return relList;
            }

            public void setRelList(List<RelListDTO> relList) {
                this.relList = relList;
            }

            public static class RelListDTO  implements Serializable {
                private String locationCode;
                private String containerSerialNumber;
                private String realCount;
                private Integer goodsAmount;
                private String relId;
                private List<String> serialNumbers;

                public String getLocationCode() {
                    return locationCode;
                }

                public void setLocationCode(String locationCode) {
                    this.locationCode = locationCode;
                }

                public String getContainerSerialNumber() {
                    return containerSerialNumber;
                }

                public void setContainerSerialNumber(String containerSerialNumber) {
                    this.containerSerialNumber = containerSerialNumber;
                }

                public String getRealCount() {
                    return realCount;
                }

                public void setRealCount(String realCount) {
                    this.realCount = realCount;
                }

                public Integer getGoodsAmount() {
                    return goodsAmount;
                }

                public void setGoodsAmount(Integer goodsAmount) {
                    this.goodsAmount = goodsAmount;
                }

                public String getRelId() {
                    return relId;
                }

                public void setRelId(String relId) {
                    this.relId = relId;
                }

                public List<String> getSerialNumbers() {
                    return serialNumbers;
                }

                public void setSerialNumbers(List<String> serialNumbers) {
                    this.serialNumbers = serialNumbers;
                }
            }
        }
    }
}
