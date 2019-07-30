package com.lv_miaoshaProject.dataobject;

import lombok.Data;

/**
 * 商品表
 */

@Data
public class Product {
    private Integer id;

    private String title;

    private Double price;

    private String description;

    private Integer sales;

    private String imgUrl;
}