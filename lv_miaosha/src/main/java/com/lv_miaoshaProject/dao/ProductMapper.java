package com.lv_miaoshaProject.dao;

import com.lv_miaoshaProject.dataobject.Product;
import com.lv_miaoshaProject.service.model.ProductModel;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    // 获取有效的商品信息列表
    List<ProductModel> getProducts();

    // 获取有效的商品信息列表总数
    int getProductsSize();
}