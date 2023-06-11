package com.ccaong.warehousingmanager.util;

import com.ccaong.warehousingmanager.bean.ChildGoodBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChildGoodsUtils {


    /**
     * 判断输入的list中,是否可以组成一个完整的货物
     *
     * @param list 子货物列表
     * @param num  一个完整的货物有几个部分组成
     * @return 组合成一个完整的货物之后，剩余的子货物列表
     */
    public static Map<String, Object> getList(List<ChildGoodBean> list, String sku, String sNo, Integer num) {

        //  判断系数
        Map map = new HashMap();
        map.put("result", false);
        map.put("list", list);
        List<String> codeList = new ArrayList();
        for (int i = 1; i <= num; i++) {
            if (i < 10) {
                codeList.add(sku + sNo + "00" + i);
            } else if (i < 100) {
                codeList.add(sku + sNo + "0" + i);
            } else {
                codeList.add(sku + sNo + "" + i);
            }
        }

        if (list.size() < num) {
            return map;
        }

        for (String code : codeList) {
            if (!loop(list, code)) {
                return map;
            }
        }

        for (String code : codeList) {
            list = removeList(list, code);
        }

        map.put("result", true);
        map.put("list", list);
        return map;
    }


    public static boolean loop(List<ChildGoodBean> list, String code) {

        for (ChildGoodBean bean : list) {
            if (code.equals(bean.getSku() + bean.getSerialNo() + bean.getCode())) {
                return true;
            }
        }
        return false;
    }


    public static List<ChildGoodBean> removeList(List<ChildGoodBean> list, String target) {

        final CopyOnWriteArrayList<ChildGoodBean> cowList = new CopyOnWriteArrayList<ChildGoodBean>(list);
        for (ChildGoodBean item : cowList) {
            if ((item.getSku() + item.getSerialNo() + item.getCode()).equals(target)) {
                cowList.remove(item);
                break;
            }
        }
        return cowList;
    }
}
