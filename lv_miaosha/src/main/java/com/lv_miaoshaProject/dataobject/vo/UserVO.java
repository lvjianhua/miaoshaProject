package com.lv_miaoshaProject.dataobject.vo;

import lombok.Data;

/**
 * 可供UI使用的VO
 */
@Data
public class UserVO {
    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telphone;

}
