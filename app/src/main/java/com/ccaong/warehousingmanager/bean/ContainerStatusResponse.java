package com.ccaong.warehousingmanager.bean;

/**
 * @author caocong
 * @date 2022/11/10
 */
public class ContainerStatusResponse {
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

        private String exist;

        private String used;

        public String getExist() {
            return exist;
        }

        public void setExist(String exist) {
            this.exist = exist;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }
    }
}
