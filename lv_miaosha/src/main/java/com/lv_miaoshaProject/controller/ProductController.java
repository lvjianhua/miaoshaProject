package com.lv_miaoshaProject.controller;

import com.lv_miaoshaProject.common.controller.BaseApiController;
import com.lv_miaoshaProject.common.response.Response;
import com.lv_miaoshaProject.common.utils.ResponseUtil;
import com.lv_miaoshaProject.dataobject.Product;
import com.lv_miaoshaProject.service.ProductService;
import com.lv_miaoshaProject.service.RedisService;
import com.lv_miaoshaProject.service.model.ProductModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品控制层
 *
 */
@Api(value = "product",description = "商品模块")
@RestController("product")
@RequestMapping("/api/product")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")// 解决跨域问题,跨域请求接收
public class ProductController extends BaseApiController<Product> {
    private ProductService productService;
    public ProductController(@Autowired ProductService productService){
        super(productService);
        this.productService = productService;
    }

    /**
     * 获取商品列表
     *
     * @return
     */
    @ApiOperation(value = "获取商品列表", httpMethod = "GET")
    @RequestMapping(value = "/getProducts", method = {RequestMethod.GET})
    public Response getProducts(
            @ApiParam(value = "当前页", name = "pageNumber", required = true)
            @RequestParam(required = true,defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "一页显示多少", name = "pageSize", required = true)
            @RequestParam(required = true,defaultValue = "10") Integer pageSize) {
        return ResponseUtil.ok(productService.getProducts(pageNumber,pageSize));
    }

    /**
     * 新增商品
     *
     * @return
     */
    @ApiOperation(value = "新增商品", httpMethod = "POST")
    @RequestMapping(value = "/saveProduct", method = {RequestMethod.POST})
    public Response saveProduct(
            @ApiParam(value = "商品对象", name = "productModel", required = true)
            @RequestBody ProductModel productModel) {
        return ResponseUtil.ok(productService.saveProduct(productModel));
    }


    /**
     * 删除商品
     *
     * @return
     */
    @ApiOperation(value = "删除商品", httpMethod = "POST")
    @RequestMapping(value = "/deleteProduct", method = {RequestMethod.POST})
    public Response deleteProduct(
            @ApiParam(value = "商品id", name = "id", required = true)
            @RequestParam Integer id) {
        return ResponseUtil.ok(productService.deleteProduct(id));
    }

    /**
     * 根据id获取商品详情
     *
     * @return
     */
    @ApiOperation(value = "根据id获取商品详情", httpMethod = "GET")
    @RequestMapping(value = "/getProductById", method = {RequestMethod.GET})
    public Response getProductById(
            @ApiParam(value = "商品id", name = "id", required = true)
            @RequestParam Integer id) {
        return ResponseUtil.ok(productService.getProductById(id));
    }

    /**
     * 扣除库存
     *
     * @return
     */
    @ApiOperation(value = "扣除库存", httpMethod = "GET")
    @RequestMapping(value = "/decreaseStock", method = {RequestMethod.GET})
    public Response decreaseStock(
            @ApiParam(value = "商品id", name = "productId", required = true)
            @RequestParam Integer productId,
            @ApiParam(value = "库存数量", name = "amount", required = true)
            @RequestParam Integer amount) {
        return ResponseUtil.ok(productService.decreaseStock(productId,amount));
    }

    /**
     * 修改商品
     *
     * @return
     */
    @ApiOperation(value = "修改商品", httpMethod = "POST")
    @RequestMapping(value = "/updateProduct", method = {RequestMethod.POST})
    public Response updateProduct(
            @ApiParam(value = "商品对象", name = "product", required = true)
            @RequestBody Product product) {
        return ResponseUtil.ok(productService.updateProduct(product));
    }


   // -------------------------------------------商品抢购---------------------------------------------
    /**
     * 【商品抢购】根据id获取商品详情
     *  需求:获取商品信息，获取库存信息
     * @return
     */
    @ApiOperation(value = "获取商品信息，获取库存信息", httpMethod = "GET")
    @RequestMapping(value = "/queryGoodsById", method = {RequestMethod.GET})
    public Response queryGoodsById(
            @ApiParam(value = "商品id", name = "id", required = true)
            @RequestParam Integer id) {
        return ResponseUtil.ok(productService.queryGoodsById(id));
    }


    /**
     * 抢购商品的方法
     *
     * @param token
     * @param id
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "抢购商品", httpMethod = "POST")
    @RequestMapping(value = "/getGoods",method = RequestMethod.POST)
    public Response getGoods(@ApiParam(value = "token", name = "token", required = true)
                             @RequestParam String token,
                             @ApiParam(value = "商品id", name = "id", required = true)
                             @RequestParam Integer id)throws Exception{
        String result = productService.getProductMessage(token,id);
        if(!"1".equals(result)){
            return ResponseUtil.error(result);
        }
        return ResponseUtil.ok();
    }

    /**
     * 轮循抢购是否成功
     *
     * @param token
     * @param id
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "轮循抢购是否成功", httpMethod = "POST")
    @RequestMapping(value = "/getProductStatus",method = RequestMethod.POST)
    public Response getProductStatus(@ApiParam(value = "token", name = "token", required = true)
                             @RequestParam String token,
                             @ApiParam(value = "商品id", name = "id", required = true)
                             @RequestParam Integer id)throws Exception{
        String result = productService.getProductStatus(token,id);
        if(!"1".equals(result)){
            return ResponseUtil.error(result);
        }
        return ResponseUtil.ok();
    }
}
