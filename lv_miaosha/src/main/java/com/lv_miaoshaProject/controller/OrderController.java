package com.lv_miaoshaProject.controller;

import com.lv_miaoshaProject.common.controller.BaseApiController;
import com.lv_miaoshaProject.common.response.Response;
import com.lv_miaoshaProject.common.utils.ResponseUtil;
import com.lv_miaoshaProject.dataobject.Order;
import com.lv_miaoshaProject.dataobject.Product;
import com.lv_miaoshaProject.dataobject.vo.OrderObject;
import com.lv_miaoshaProject.service.OrderService;
import com.lv_miaoshaProject.service.ProductService;
import com.lv_miaoshaProject.service.model.ProductModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制层
 *
 */
@Api(value = "order",description = "订单模块")
@RestController("order")
@RequestMapping("/api/order")
public class OrderController extends BaseApiController<Order> {

    private OrderService orderService;
    public OrderController(@Autowired OrderService orderService){
        super(orderService);
        this.orderService = orderService;
    }

    /**
     * 订单生成
     *
     * @return
     */
    @ApiOperation(value = "订单生成", httpMethod = "POST")
    @RequestMapping(value = "/createOrder", method = {RequestMethod.POST})
    public Response createOrder(
            @ApiParam(value = "订单生成对象", name = "orderObject", required = true)
            @RequestBody OrderObject orderObject) {
        return ResponseUtil.ok(orderService.createOrder(orderObject));
    }


    /**
     * 抢购订单生成
     *
     * @return
     */
    @ApiOperation(value = "抢购订单生成", httpMethod = "POST")
    @RequestMapping(value = "/qkCreateOrder", method = {RequestMethod.POST})
    public void qkCreateOrder(
            @ApiParam(value = "订单生成对象", name = "orderObject", required = true)
            @RequestBody OrderObject orderObject) {
        orderService.qkCreateOrder(orderObject);
    }

}
