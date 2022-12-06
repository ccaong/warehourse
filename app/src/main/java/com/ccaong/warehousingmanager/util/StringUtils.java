package com.ccaong.warehousingmanager.util;

/**
 * @author eyecool
 * @date 2022/10/10
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        return "".equals(str);
    }
}
