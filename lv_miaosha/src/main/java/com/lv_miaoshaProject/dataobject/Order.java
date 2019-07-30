package com.lv_miaoshaProject.dataobject;

import lombok.Data;

/**
 * 订单表
 */
@Data
public class Order {
    private Integer id;

    private String orderCode;

    private Integer userId;

    private Integer productId;

    private Double productPrice;

    private Integer qty;

    private Double orderPrice;
}