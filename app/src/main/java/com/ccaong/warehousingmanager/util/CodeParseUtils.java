package com.ccaong.warehousingmanager.util;

import android.util.Log;

import com.ccaong.warehousingmanager.config.Constant;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫码结果解析
 *
 * @author eyecool
 * @date 2022/9/28
 */
public class CodeParseUtils {

    private static final String TAG = CodeParseUtils.class.getSimpleName();

    /**
     * 根据扫码结果判断类型
     *
     * @param code
     * @return
     */
    public static int getCodeType(String code) {

        if (code == null || code.equals("")) {
            return -1;
        }

        if (code.startsWith("C") || code.startsWith("T") || code.startsWith("Z")) {
            // 载具
            return 1;
        }

        if (isGoodsCode(code)) {
            // 物料
            return 2;
        }

        return -1;
    }


    /**
     * 解析物料码，获取SKU
     */
    public static String getGoodSkuCode(String code) {
        if (isGoodsCode(code)) {
            String[] sArray = code.split("_");
            int index = Hawk.get(Constant.RULES_SKU_INDEX, 1);
            if (sArray.length > index) {
                return sArray[index];
            }
        } else {
            Log.e(TAG, "不是物料码");
        }
        return "";
    }

    /**
     * 判断是是否是物料码
     *
     * @param string 扫描得到的编码
     * @return true 物料码，false 物料码
     */
    public static boolean isGoodsCode(String string) {
        // 先判断是否有配置的物料码规则
        if (Hawk.get(Constant.RULES_SHOW_GOOD, "").equals("")) {
            return isDefaultGoodsCode(string);
        }
        return isRules(string, Constant.RULES_CODE_GOOD);
    }

    /**
     * 判断是否为货物码
     *
     * @param code
     * @return
     */
    public static boolean isDefaultGoodsCode(String code) {

        if (code == null || code.equals("")) {
            return false;
        }

        String[] sArray = code.split("_");
        if (sArray.length == 4) {
            if (sArray[0].length() == 4 && sArray[2].length() == 3) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是是否是位置码
     *
     * @param string 扫描得到的编码
     * @return true 位置码，false 不是位置码
     */
    public static boolean isLocalCode(String string) {
        if (Hawk.get(Constant.RULES_SHOW_LOCAL, "").equals("")) {
            return isDefaultLocalCode(string);
        }
        return isRules(string, Constant.RULES_CODE_LOCAL);
    }

    /**
     * 判断是否为位置
     *
     * @param code
     * @return
     */
    public static boolean isDefaultLocalCode(String code) {

        if (code == null || code.equals("")) {
            return false;
        }

        String[] sArray = code.split("_");
        if (sArray.length == 4) {
            if (sArray[0].length() == 1 && sArray[1].length() == 1 && sArray[2].length() == 1 && sArray[3].length() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是是否是载具
     *
     * @param string 扫描得到的编码
     * @return true 载具码，false 不是载具码
     */
    public static boolean isCodeContainer(String string) {
        // 先判断是否有载具码规则
        if (Hawk.get(Constant.RULES_SHOW_CONTAINER, "").equals("")) {
            // 判断是否为物料码或者位置码，如果不是，就认定为位置码
            if (isGoodsCode(string)) {
                return false;
            } else if (isLocalCode(string)) {
                return false;
            } else {
                return true;
            }
        } else {
            // 如果有规则，就按照规则判断
            return isRules(string, Constant.RULES_CODE_CONTAINER);
        }
    }

    /**
     * 是否符合规则
     *
     * @param string
     * @return
     */
    public static boolean isRules(String string, String rules) {
        //规则规定的长度
        int rulesSize = Hawk.get(rules + Constant.RULES_SIZE);
        String[] sArray = string.split("_");

        if (sArray.length == rulesSize) {
            // 判断每个部分的长度
            for (int i = 0; i < sArray.length; i++) {
                // 规则的长度
                int ruleLength = Hawk.get(rules + Constant.RULES_UNIT_LENGTH + "_" + i);
                // 实际的长度
                int length = sArray[i].length();

                if (ruleLength != -1) {
                    // 判断实际长度和规定的长度是否相同
                    if (length == ruleLength) {
                        // 长度相同，再判断规则中的字母所处的位置和实际是否一样
                        List<Integer> list = Hawk.get(rules + Constant.RULES_UNIT_A_INDEX + "_" + i);
                        // 获取实际中字母所处的位置
                        List<Integer> indexs = searchAllIndex(sArray[i]);
                        // 判断字母所处位置是否一致
                        if (!isEquals(indexs, list)) {
                            // 两者位置不同，不符合规则
                            return false;
                        }

                    } else {
                        // 长度不同，不符合规则
                        return false;
                    }
                }
            }
        } else {
            // 分隔符数量不一致，不符合规则
            return false;
        }
        return true;
    }

    /**
     * 获取字母所处的位置
     *
     * @param str 字符串
     * @return 字母所处的位置
     */
    public static List<Integer> searchAllIndex(String str) {
        List<Integer> list = new ArrayList<>();


        char c[] = str.toCharArray();
        byte b[] = new byte[c.length];

        for (int i = 0; i < c.length; i++) {
            b[i] = (byte) c[i];
            // 对每一个字符进行判断
            if ((b[i] >= 97 && b[i] <= 122) || (b[i] >= 65 && b[i] <= 90)) {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * 判断两个list是都一致
     *
     * @param list1
     * @param list2
     * @return
     */
    public static boolean isEquals(List<Integer> list1, List<Integer> list2) {
        if (null != list1 && null != list2) {
            if (list1.containsAll(list2) && list2.containsAll(list1)) {
                return true;
            }
            return false;
        }
        return true;
    }
}
