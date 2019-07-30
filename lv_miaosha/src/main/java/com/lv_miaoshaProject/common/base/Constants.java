package com.lv_miaoshaProject.common.base;

/**
 * 常量类
 *
 */
public class
Constants {
    // 登录超时时间:30分钟
    public static final Long loginExpire = 1800000L;

    // 抢购商品锁超时时间:30分钟
    public static final Long lockExpire = 1800000L;

    // token前缀
    public static final String tokenPrefix = "token:";

    // 抢购商品前缀
    public static final String goodsPrefix = "qk:";

    // 已抢购到的商品前缀
    public static final String getProductPrefix = "getProductPrefix:";

    // 抢购锁前缀
    public static final String lockPrefix = "lock:";

    // 库存临时表状态
    public static final class StockStatus{
        public static final Integer lock = 0;
        public static final Integer paySuccess = 1;
        public static final Integer payOverTime = 2;
    }

    // 用户抢购标识
    public static final class getProductStatus{
        public static final Integer getSuccess = 1;
        public static final Integer getFail = 0;
    }

    /**
     * ActiveMQ消息名称
     */
    public static final class ActivieMQMessage{
        // 抢购的消息名称
        public static final String getMessage = "get:message";
    }
}
