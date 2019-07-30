package com.lv_miaoshaProject.dataobject;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户表
 */
@Data
public class UserDO implements Serializable {
    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telphone;

    private String registerMode;

    private Integer thirdPartyId;
}