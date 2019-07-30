package com.lv_miaoshaProject.dao;

import com.lv_miaoshaProject.dataobject.Product_Stock;
import org.apache.ibatis.annotations.Param;

public interface Product_StockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product_Stock record);

    int insertSelective(Product_Stock record);

    Product_Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product_Stock record);

    int updateByPrimaryKey(Product_Stock record);

    Product_Stock selectByProductId(Integer productId);

    int decreaseStock(@Param("productId")Integer productId,@Param("amount")Integer amount);
}