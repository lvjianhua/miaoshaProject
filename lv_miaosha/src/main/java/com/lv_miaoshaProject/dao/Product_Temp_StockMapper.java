package com.lv_miaoshaProject.dao;

import com.lv_miaoshaProject.dataobject.Product_Temp_Stock;

import java.util.Map;

public interface Product_Temp_StockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product_Temp_Stock record);

    int insertSelective(Product_Temp_Stock record);

    Product_Temp_Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product_Temp_Stock record);

    int updateByPrimaryKey(Product_Temp_Stock record);

    Integer getProductTempCountByParam(Product_Temp_Stock record);

    Integer getProductTempStockByData(Product_Temp_Stock pts);
}