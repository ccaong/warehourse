package com.ccaong.warehousingmanager.bean;

import java.util.List;

/**
 * @author eyecool
 * @date 2022/9/28
 */
public class PullTaskDetailResponse {

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
        private String orderNumber;
        private String createTime;
        private List<ContainerCodesDTO> containerCodes;
        private String source;
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

        public List<ContainerCodesDTO> getContainerCodes() {
            return containerCodes;
        }

        public void setContainerCodes(List<ContainerCodesDTO> containerCodes) {
            this.containerCodes = containerCodes;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static class ContainerCodesDTO {
            private String id;
            private String containerCode;
            private String locationCode;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContainerCode() {
                return containerCode;
            }

            public void setContainerCode(String containerCode) {
                this.containerCode = containerCode;
            }

            public String getLocationCode() {
                return locationCode;
            }

            public void setLocationCode(String locationCode) {
                this.locationCode = locationCode;
            }
        }
    }
}
