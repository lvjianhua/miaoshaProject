package com.lv_miaoshaProject.dataobject;

import lombok.Data;

@Data
public class Product_Temp_Stock {
    private Integer id;

    private Integer productId;

    private Integer userId;

    /**
     * 0:锁定,1:支付成功,2:超时未支付
     */
    private Integer status;

    private Integer stock;
}