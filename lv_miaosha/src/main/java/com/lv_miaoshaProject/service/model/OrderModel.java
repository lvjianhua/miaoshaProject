package com.lv_miaoshaProject.service.model;

import lombok.Data;

/**
 * 订单模型
 */
@Data
public class OrderModel {
    // 订单号
    private String orderCode;

    // 用户id
    private Integer userId;

    // 商品id
    private Integer productId;

    // 购买的件数
    private Integer qty;

    // 购买商品单价
    private Double productPrice;

    // 购买金额
    private Double orderPrice;
}
