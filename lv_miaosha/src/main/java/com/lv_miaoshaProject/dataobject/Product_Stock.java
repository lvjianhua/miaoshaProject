package com.lv_miaoshaProject.dataobject;

import lombok.Data;

/**
 * 商品库存表
 *
 */
@Data
public class Product_Stock {
    private Integer id;

    private Integer stock;

    private Integer productId;
}