package com.ccaong.warehousingmanager.bean;

import java.util.List;

/**
 * @author caocong
 * @date 2022/9/28
 */
public class PutTaskDetailResponse {


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
        private List<ResultDTO> result;
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

        public List<ResultDTO> getResult() {
            return result;
        }

        public void setResult(List<ResultDTO> result) {
            this.result = result;
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

        public static class ResultDTO {
            private String containerCode;
            private String location;
            private List<ListDTO> list;

            public String getContainerCode() {
                return containerCode;
            }

            public void setContainerCode(String containerCode) {
                this.containerCode = containerCode;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public List<ListDTO> getList() {
                return list;
            }

            public void setList(List<ListDTO> list) {
                this.list = list;
            }

            public static class ListDTO {
                private ParamsDTO params;
                private String id;
                private String containerCode;

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

                public String getContainerCode() {
                    return containerCode;
                }

                public void setContainerCode(String containerCode) {
                    this.containerCode = containerCode;
                }

                public static class ParamsDTO {
                }
            }
        }
    }
}
