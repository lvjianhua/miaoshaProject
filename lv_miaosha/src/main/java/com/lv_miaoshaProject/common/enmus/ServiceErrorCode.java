package com.lv_miaoshaProject.common.enmus;

/**
 * Service实现层提示错误枚举
 *
 */
public enum ServiceErrorCode implements ErrorCodeInterface {

    WRONG_DATA(101, "非法参数"),

    FAILED_MOBILE_RECEIPT(104,"操作失败，请检查您的信息！"),

    WRONG_DATE(107,"日期格式错误"),

    WRONG_MESSAGE_CODE(108,"验证码错误！"),

    FAILED_SEND_MAIL(109,"邮件发送失败！"),

    CANNOT_OPERATE(110,"不能执行该操作！"),

    NO_USER(111,"没有该用户！"),

    REPEAT_PHONE(112,"手机号已存在！"),

    OBJECT_IS_NULL(113,"对象为空！"),

    LOGIN_ERROR(114,"用户名或密码有误！"),

    LOGIN_FAIL(116,"登录失败！"),

    NO_LOGIN(117,"用户未登录！"),

    REPEAT_TITLE(115,"商品已存在！"),

    STOCK_NO_ENOUGH(200,"库存不足！");

    private int code;
    private String message;

    ServiceErrorCode(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

