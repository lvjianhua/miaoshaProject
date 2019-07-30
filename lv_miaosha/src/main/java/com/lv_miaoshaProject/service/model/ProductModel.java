package com.lv_miaoshaProject.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品模型
 */
@Data
public class ProductModel {
    // 主键id
    private Integer id;
    // 商品名称
    private String title;
    // 价格
    private Double price;
    // 库存
    private Integer stock;
    // 实际库存
    private Integer currentStock;
    // 描述
    private String description;
    // 销量
    private Integer sales;
    // 商品图片url
    private String imgUrl;
}
