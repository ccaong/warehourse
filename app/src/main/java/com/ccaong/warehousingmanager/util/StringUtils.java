package com.ccaong.warehousingmanager.util;

import java.util.Arrays;

/**
 * @author caocong
 * @date 2022/10/10
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        return "".equals(str);
    }

    public static boolean isContain(String[] arr, String targetValue) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        return Arrays.asList(arr).contains(targetValue);
    }

}
