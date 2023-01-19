package com.ganpengyu.ess.studio.ess.common;

import java.util.Arrays;

/**
 * 电子签章工具类
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
public class EssUtils {

    /**
     * 判断字符串为空
     *
     * @param charSequence 待检查字符串
     * @return true 字符串为空 false 字符串不为空
     */
    public static boolean isStringEmpty(CharSequence charSequence) {
        return null == charSequence || charSequence.length() == 0;
    }

    /**
     * 判断字符串不为空
     *
     * @param charSequence 待检查字符串
     * @return true 字符串不为空 false 字符串为空
     */
    public static boolean isStringNotEmpty(CharSequence charSequence) {
        return !isStringEmpty(charSequence);
    }

    /**
     * 判断数组为空
     *
     * @param array 待检查数组
     * @return true 数组为空 false 数组不为空
     */
    public static boolean isArrayEmpty(Object[] array) {
        return null == array || array.length == 0;
    }

    /**
     * 判断数组不为空
     *
     * @param array 待检查数组
     * @return true 数组不为空 false 数组为空
     */
    public static boolean isArrayNotEmpty(Object[] array) {
        return !isArrayEmpty(array);
    }

    /**
     * 判断字符串数组中的所有元素是否都为空字符串
     *
     * @param stringArray 待检查数组
     * @return true 元素全为空字符串 false 有不为空字符串的元素
     */
    public static boolean isArrayOnlyContainsEmptyString(String[] stringArray) {
        return Arrays.stream(stringArray).noneMatch(EssUtils::isStringNotEmpty);
    }

    /**
     * 判断字符串数组中的所有元素是否都为空字符串
     *
     * @param stringArray 待检查数组
     * @return true 元素全为空字符串 false 有不为空字符串的元素
     */
    public static boolean isArrayNotContainsEmptyString(String[] stringArray) {
        return Arrays.stream(stringArray).noneMatch(EssUtils::isStringEmpty);
    }

}
