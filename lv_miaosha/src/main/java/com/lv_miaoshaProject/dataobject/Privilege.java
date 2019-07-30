package com.lv_miaoshaProject.dataobject;

import lombok.Data;

@Data
public class Privilege {
    private Integer id;

    private String privilegeName;

    private String remark;

    private String url;
}