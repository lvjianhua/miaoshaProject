package com.lv_miaoshaProject.service.impl;

import com.lv_miaoshaProject.common.enmus.ServiceErrorCode;
import com.lv_miaoshaProject.common.exception.ServiceRunTimeException;
import com.lv_miaoshaProject.common.utils.PBMeth;
import com.lv_miaoshaProject.dao.OrderMapper;
import com.lv_miaoshaProject.dao.SequenceInfoMapper;
import com.lv_miaoshaProject.dataobject.Order;
import com.lv_miaoshaProject.dataobject.Product;
import com.lv_miaoshaProject.dataobject.SequenceInfo;
import com.lv_miaoshaProject.dataobject.UserDO;
import com.lv_miaoshaProject.dataobject.vo.OrderObject;
import com.lv_miaoshaProject.service.OrderService;
import com.lv_miaoshaProject.service.ProductService;
import com.lv_miaoshaProject.service.UserService;
import com.lv_miaoshaProject.service.model.OrderModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 接口实现类
 *
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SequenceInfoMapper sequenceInfoMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public Order getById(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Order object) {
        return orderMapper.updateByPrimaryKey(object);
    }

    @Override
    public int save(Order object) {
        return orderMapper.insertSelective(object);
    }

    @Override
    public OrderModel createOrder(OrderObject orderObject) {
        chackOrderObject(orderObject);
        // 校验商品
        Product product = productService.getById(orderObject.getProductId());
        if (product == null){
            throw new ServiceRunTimeException("商品信息不存在!");
        }
        UserDO user = userService.getById(orderObject.getUserId());
        if (user == null){
            throw new ServiceRunTimeException("用户信息不存在!");
        }
        if (orderObject.getQty() <= 0 || orderObject.getQty() > 100){
            throw new ServiceRunTimeException("数量信息不正确!");
        }
        // 校验库存扣减是否成功
        boolean result = productService.decreaseStock(orderObject.getProductId(),orderObject.getQty());
        if(!result){
            throw new ServiceRunTimeException(ServiceErrorCode.STOCK_NO_ENOUGH);
        }
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(orderObject.getUserId());
        orderModel.setProductId(orderObject.getProductId());
        orderModel.setQty(orderObject.getQty());
        orderModel.setProductPrice(product.getPrice());
        Double totalMoney = PBMeth.round(product.getPrice()*orderObject.getQty(),2);
        orderModel.setOrderPrice(totalMoney);

        Order order = pageOrder(orderModel);
        order.setOrderCode(getOrderCode());
        orderMapper.insertSelective(order);

        // 返回前端
        return orderModel;
    }

    // 订单16位返回
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String getOrderCode() {
        StringBuffer orderCode = new StringBuffer();
        // 前8位年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        orderCode.append(nowDate);
        // 中6位自增系列：保证订单不重复
        // 获取当前sequence
        int sequence = 0;
        SequenceInfo sequenceInfo = sequenceInfoMapper.getSequenceByName("order_info");
        sequence = sequenceInfo.getCurrentValue();
        sequenceInfo.setCurrentValue(sequenceInfo.getCurrentValue()+sequenceInfo.getStep());
        sequenceInfoMapper.updateByPrimaryKeySelective(sequenceInfo);
        String sequenceStr = String.valueOf(sequence);
        // 存在超过6位数的情况，可在表中添加一个最大值的字段，当currentValue+1>最大值，currentValue从0开始
        for(int i=0;i<6-sequenceStr.length();i++){
            orderCode.append(0);
        }
        orderCode.append(sequenceStr);
        // 后两位为分库分表位
        orderCode.append("00");
        return orderCode.toString();
    }

    private Order pageOrder(OrderModel orderModel) {
        Order order = new Order();
        if(orderModel != null){
            BeanUtils.copyProperties(orderModel,order);
        }
        return order;
    }

    private void chackOrderObject(OrderObject orderObject) {
        if (orderObject.getUserId() == null){
            throw new ServiceRunTimeException("用户id不能为空!");
        }
        if (orderObject.getProductId() == null){
            throw new ServiceRunTimeException("下单商品id不能为空!");
        }
        if (orderObject.getQty() == null){
            throw new ServiceRunTimeException("下单数量不能为空!");
        }
    }



    @Override
    public void qkCreateOrder(OrderObject orderObject) {
        chackOrderObject(orderObject);
        // 校验商品
        Product product = productService.getById(orderObject.getProductId());
        if (product == null){
            throw new ServiceRunTimeException("商品信息不存在!");
        }
        UserDO user = userService.getById(orderObject.getUserId());
        if (user == null){
            throw new ServiceRunTimeException("用户信息不存在!");
        }
        if (orderObject.getQty() <= 0 || orderObject.getQty() > 100){
            throw new ServiceRunTimeException("数量信息不正确!");
        }
        // 校验库存扣减是否成功
        boolean result = productService.decreaseStock(orderObject.getProductId(),orderObject.getQty());
        if(!result){
            throw new ServiceRunTimeException(ServiceErrorCode.STOCK_NO_ENOUGH);
        }
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(orderObject.getUserId());
        orderModel.setProductId(orderObject.getProductId());
        orderModel.setQty(orderObject.getQty());
        orderModel.setProductPrice(product.getPrice());
        Double totalMoney = PBMeth.round(product.getPrice()*orderObject.getQty(),2);
        orderModel.setOrderPrice(totalMoney);

        Order order = pageOrder(orderModel);
        order.setOrderCode(getOrderCode());
        orderMapper.insertSelective(order);
    }

}


