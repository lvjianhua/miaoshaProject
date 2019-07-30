package com.lv_miaoshaProject.service;

import com.lv_miaoshaProject.common.base.BaseLogicService;
import com.lv_miaoshaProject.common.utils.PageBean;
import com.lv_miaoshaProject.dataobject.Product;
import com.lv_miaoshaProject.service.model.ProductModel;

/**
 * 商品接口
 */
public interface ProductService extends BaseLogicService<Product> {

    PageBean<ProductModel> getProducts(Integer pageNumber, Integer pageSize);

    ProductModel saveProduct(ProductModel productModel);

    ProductModel getProductById(Integer id);

    boolean decreaseStock(Integer productId, Integer amount);

    boolean deleteProduct(Integer id);

    boolean updateProduct(Product product);

    // 【商品抢购】需求:获取商品信息，获取库存信息
    ProductModel queryGoodsById(Integer id);

    // 抢购商品的方法
    String getProductMessage(String token, Integer id);

    // 抢购是否成功
    String getProductStatus(String token, Integer id);
}
