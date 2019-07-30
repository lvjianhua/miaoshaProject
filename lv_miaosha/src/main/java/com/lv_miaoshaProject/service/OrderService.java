package com.lv_miaoshaProject.service;

import com.lv_miaoshaProject.common.base.BaseLogicService;
import com.lv_miaoshaProject.dataobject.Order;
import com.lv_miaoshaProject.dataobject.vo.OrderObject;
import com.lv_miaoshaProject.service.model.OrderModel;

/**
 * 订单接口
 */
public interface OrderService extends BaseLogicService<Order> {

    OrderModel createOrder(OrderObject orderObject);

    void qkCreateOrder(OrderObject orderObject);
}
