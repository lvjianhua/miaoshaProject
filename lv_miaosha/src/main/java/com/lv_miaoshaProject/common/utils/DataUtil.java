package com.lv_miaoshaProject.common.utils;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;

/**
 * 数据处理
 *
 * @author admin
 *
 */
public class DataUtil {

    public static double ifNull(Double d) {
        return d == null ? 0 : d;
    }

    public static int ifNull(Integer i) {
        return i == null ? 0 : i;
    }

    /**
     * @param money
     * @return
     */
    public static Long getMoney(Double money) {
        return 	BigDecimal.valueOf(money)
                .multiply(BigDecimal.valueOf(10000L)).longValue();
    }

    public static Long getMoney(String money) {
        return 	BigDecimal.valueOf(Double.valueOf(money))
                .multiply(BigDecimal.valueOf(10000L)).longValue();
    }

    public static Double getMoney(Long money) {
        return 	BigDecimal.valueOf(money)
                .divide(BigDecimal.valueOf(10000L)).doubleValue();
    }

    /**
     * 是否是负数
     * @param num
     * @return
     */
    public static boolean isNegative(int num) {
        if (num < 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否是负数
     * @param num
     * @return
     */
    public static boolean isNegative(long num) {
        if (num < 0) {
            return true;
        }
        return false;
    }

    public static Double round(double d, int num) {
        return Double.parseDouble(String.format("%." + num + "f", d));
    }

    /**
     * 是否是负数
     * @param jsonStr json字符
     * @return 想要的对象
     */
    public static <T> T getObjectByJsonStr(String jsonStr,Class<T> clzs) {
        if(StringUtils.isEmpty(jsonStr)){
            try {
                return clzs.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return (T) JSON.parseObject(jsonStr,clzs);
        }
    }
}
