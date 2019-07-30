package com.lv_miaoshaProject.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.lv_miaoshaProject.common.base.Constants;
import com.lv_miaoshaProject.common.enmus.ServiceErrorCode;
import com.lv_miaoshaProject.common.exception.ServiceRunTimeException;
import com.lv_miaoshaProject.common.utils.ActiveMQUtils;
import com.lv_miaoshaProject.common.utils.IdWorker;
import com.lv_miaoshaProject.common.utils.PageBean;
import com.lv_miaoshaProject.dao.ProductMapper;
import com.lv_miaoshaProject.dao.Product_StockMapper;
import com.lv_miaoshaProject.dao.Product_Temp_StockMapper;
import com.lv_miaoshaProject.dataobject.Product;
import com.lv_miaoshaProject.dataobject.Product_Stock;
import com.lv_miaoshaProject.dataobject.Product_Temp_Stock;
import com.lv_miaoshaProject.dataobject.vo.OrderObject;
import com.lv_miaoshaProject.service.OrderService;
import com.lv_miaoshaProject.service.ProductService;
import com.lv_miaoshaProject.service.RedisService;
import com.lv_miaoshaProject.service.model.GetProductMessage;
import com.lv_miaoshaProject.service.model.ProductModel;
import com.lv_miaoshaProject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口实现类
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper ProductMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private Product_StockMapper product_StockMapper;

    @Autowired
    private Product_Temp_StockMapper product_Temp_StockMapper;

    @Autowired
    private ActiveMQUtils activeMQUtils;

    @Autowired
    private OrderService orderService;

    @Override
    public PageBean<ProductModel> getProducts(Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<ProductModel> productModels = ProductMapper.getProducts();
        int count = ProductMapper.getProductsSize();
        PageBean<ProductModel> pageData = new PageBean<>(pageNumber, pageSize, count);
        pageData.setData(productModels);
        return pageData;
    }

    @Override
    @Transactional
    public ProductModel saveProduct(ProductModel productModel) {
        chack(productModel);
        Product product = new Product();
        BeanUtils.copyProperties(productModel,product);
        // 新增商品,捕获商品名唯一异常
        try{
            ProductMapper.insertSelective(product);
        }catch (DuplicateKeyException ex){
            throw new ServiceRunTimeException(ServiceErrorCode.REPEAT_TITLE);
        }
        Product_Stock product_stock = new Product_Stock();
        product_stock.setProductId(product.getId());
        product_stock.setStock(productModel.getStock());
        product_StockMapper.insertSelective(product_stock);
        return pageProductModel(product.getId());
    }

    @Override
    public ProductModel getProductById(Integer id) {
        return pageProductModel(id);
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer productId, Integer amount) {
        int affectedRow = product_StockMapper.decreaseStock(productId,amount);
        if(affectedRow > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(Integer id) {
        int result = ProductMapper.deleteByPrimaryKey(id);
        if(result != 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProduct(Product product) {
        int result = ProductMapper.updateByPrimaryKeySelective(product);
        if(result != 1){
            return false;
        }
        return true;
    }


    private void chack(ProductModel productModel) {
/*        if(StringUtils.isEmpty(productModel.getTitle())){
            throw new ServiceRunTimeException("商品名称不能为空!");
        }
        if(productModel.getPrice() != null){
            throw new ServiceRunTimeException("商品价格不能为空!");
        }
        if(productModel.getStock() != null){
            throw new ServiceRunTimeException("商品库存不能为空!");
        }*/
    }

    private ProductModel pageProductModel(Integer id) {
        ProductModel productModel = new ProductModel();
        Product product = getById(id);
        BeanUtils.copyProperties(product,productModel);
        Product_Stock product_stock = product_StockMapper.selectByProductId(id);
        productModel.setStock(product_stock.getStock());
        return productModel;
    }

    @Override
    public Product getById(Integer id) {
        return ProductMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Product object) {
        return ProductMapper.updateByPrimaryKey(object);
    }

    @Override
    public int save(Product object) {
        return ProductMapper.insertSelective(object);
    }


    //---------------------------------------------商品抢购---------------------------------------------------------
    @Override
    public ProductModel queryGoodsById(Integer id) {
        ProductModel productModel = null;
        // 1.首先从redis中进行获取
        // 2.redis中没有，则走数据库查询，并将结果写入到redis
        // 3.redis中有，则走redis
        String goodsVoJson = (String) redisService.get(Constants.goodsPrefix+id);
        if(ObjectUtils.isEmpty(goodsVoJson)){
            productModel = new ProductModel();
            Product product = getById(id);
            BeanUtils.copyProperties(product,productModel);
            Product_Stock product_stock = product_StockMapper.selectByProductId(id);
            productModel.setStock(product_stock.getStock());
            // 获取库存信息
            // 1.获取临时库存表中，goods_id 为id的有效的记录数->用户已消费或待消费记录数
            Product_Temp_Stock pts = new Product_Temp_Stock();
            pts.setProductId(id);
            pts.setStatus(1);
            Integer activeCount = product_Temp_StockMapper.getProductTempCountByParam(pts);
            // 实际库存 = 商品表的原始库存-临时库存表的有效库存(锁定的，支付成功的)
            Integer currentCount= productModel.getStock()-activeCount;
            productModel.setCurrentStock(currentCount);
            // 放在redis当中
            redisService.set(Constants.goodsPrefix+id, productModel);
        }else{
            productModel = JSONObject.parseObject(goodsVoJson,ProductModel.class);
        }
        return productModel;
    }

    /**
     * 处理抢购请求
     *
     * @param getProductMessage
     * @throws Exception
     */
    @JmsListener(destination = Constants.ActivieMQMessage.getMessage)
    public void getGoods(GetProductMessage getProductMessage) throws Exception {
        Integer userId = getProductMessage.getUserId();
        Integer id = getProductMessage.getProductId();
        // 添加分布式锁
        while (!redisService.setNX(Constants.lockPrefix+id,Constants.lockExpire)){
            Thread.sleep(3);
        }

        // 1.查看用户是否已经抢购过该商品，如果用户有抢购成功未支付的或已经支付成功的记录，则不能抢
        String getFlag = (String) redisService.get(Constants.getProductPrefix+id+":"+userId);
        if(!ObjectUtils.isEmpty(getFlag) && getFlag.equals(String.valueOf(Constants.getProductStatus.getSuccess))){
            // 释放锁
            redisService.del(Constants.lockPrefix+id);
            return;
        }

        // 2.判断库存是否大于0,如果大于0则进入抢购环节
        String goodsVoJson = (String) redisService.get(Constants.goodsPrefix+id);
        ProductModel productModel = JSONObject.parseObject(goodsVoJson,ProductModel.class);
        if(productModel.getCurrentStock() <= 0){
            redisService.del(Constants.lockPrefix+id);// 释放锁
            redisService.set(Constants.getProductPrefix+id+":"+userId,Constants.getProductStatus.getFail);
            return;
        }

        // 3.更新库存 包括 更新临时库存表（插入记录）以及更新reids数据
        saveProduct_Temp_Stock(userId,productModel.getId());

        productModel.setCurrentStock(productModel.getCurrentStock()-1); // 当前库存减一
        System.out.print("--------productModel:"+productModel);
        redisService.set(Constants.goodsPrefix+id,productModel);

        // 4.生成订单
        OrderObject oo = new OrderObject();
        oo.setProductId(id);
        oo.setQty(1);
        oo.setUserId(userId);
        orderService.qkCreateOrder(oo);

        // 5.在redis中，增加用户已经抢购到的商品标识
        redisService.set(Constants.getProductPrefix+id+":"+userId,Constants.getProductStatus.getSuccess);
        redisService.del(Constants.lockPrefix+id);// 释放锁
    }

    @Override
    public String getProductMessage(String token, Integer id) {
        // 根据token知道是那个用户 获取用户信息
        String userJson = (String) redisService.get(token);
        UserModel userModel = JSONObject.parseObject(userJson,UserModel.class);
        if(ObjectUtils.isEmpty(userModel)){
            return "need login!";
        }
        GetProductMessage gom = new GetProductMessage();
        gom.setUserId(userModel.getId());
        gom.setProductId(id);
        gom.setRecevieDate(new Date());
        activeMQUtils.sendQueueMessage(Constants.ActivieMQMessage.getMessage,gom);
        return "1";
    }

    /**
     * 在redis中查询抢购状态
     *
     * @param token
     * @param id
     * @return
     */
    @Override
    public String getProductStatus(String token, Integer id) {
        // 1.根据token知道是那个用户 获取用户信息
        String userJson = (String) redisService.get(token);
        UserModel userModel = JSONObject.parseObject(userJson,UserModel.class);
        // 2.根据用户信息和商品获取抢购状态
        String flag = (String)redisService.get(Constants.getProductPrefix+id+":"+userModel.getId());
        if(ObjectUtils.isEmpty(flag)){
            return "目前正在排队!";
        }else if("0".equals(flag)){
            return "抢购失败!";
        }else{
            return "抢购成功";
        }
    }

    /**
     * 保存用户临时抢购信息到临时库存表
     *
     * @param userId
     * @param productId
     */
    private void saveProduct_Temp_Stock(Integer userId,Integer productId) {
        Product_Temp_Stock Product_Temp_Stock = new Product_Temp_Stock();
        Product_Temp_Stock.setUserId(userId);
        Product_Temp_Stock.setProductId(productId);
        Product_Temp_Stock.setStatus(Constants.StockStatus.lock);
        Product_Temp_Stock.setStock(1);
        product_Temp_StockMapper.insertSelective(Product_Temp_Stock);
    }


}


