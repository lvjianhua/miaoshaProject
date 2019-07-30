package com.lv_miaoshaProject.dataobject.vo;

import lombok.Data;

/**
 * 用户注册对象
 */
@Data
public class Register {

    private String name;

    private Byte gender;

    private Integer age;

    private String encrptPassword;

    private String telphone;

    private String otpCode;

}
