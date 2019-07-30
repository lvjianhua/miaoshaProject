package com.lv_miaoshaProject.dataobject.vo;

import lombok.Data;

/**
 * 下单Object
 */
@Data
public class OrderObject {

    private Integer userId;

    private Integer productId;

    private Integer qty;
}
