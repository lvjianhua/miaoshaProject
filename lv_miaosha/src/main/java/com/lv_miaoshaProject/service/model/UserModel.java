package com.lv_miaoshaProject.service.model;

import lombok.Data;

/**
 * 用户模型Model
 */
@Data
public class UserModel {
    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telphone;

    private String registerMode;

    private Integer thirdPartyId;

    private String encrptPassword;
}
