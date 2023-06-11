package com.ccaong.warehousingmanager.bean;

/**
 * @author caocong
 * @date 2022/9/19
 */
public class TestBean {

    public String name;

    public String time;

    public String LongString;

    public String sortString;

    private int num;


    public String other;

    public TestBean() {
    }

    public TestBean(String name, String other, int num) {
        this.name = name;
        this.other = other;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getLongString() {
        return "哈哈哈哈哈哈哈哈哈哈呵呵呵呵呵呵呵呵呵呵呵呵嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嗯嗯嗯嗯嗯嗯哦哦哦哦哦哦哦哦哦哦哦哦哦哦";
    }

    public void setLongString(String longString) {
        LongString = longString;
    }

    public String getSortString() {
        return "哈哈";
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public String getTime() {
        return "2022-01-01 12:00:00";
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
