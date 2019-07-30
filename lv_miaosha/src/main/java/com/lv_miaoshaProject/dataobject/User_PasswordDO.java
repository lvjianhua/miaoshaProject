package com.lv_miaoshaProject.dataobject;

import lombok.Data;

/**
 * 用户密码表
 */
@Data
public class User_PasswordDO {
    private Integer id;

    private String encrptPassword;

    private Integer userId;
}