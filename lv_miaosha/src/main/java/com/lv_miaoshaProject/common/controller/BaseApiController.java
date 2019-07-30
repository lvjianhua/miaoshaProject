package com.lv_miaoshaProject.common.controller;

import com.lv_miaoshaProject.common.base.BaseLogicService;
import com.lv_miaoshaProject.common.response.Response;
import com.lv_miaoshaProject.common.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class BaseApiController<T> extends BaseController {
    private BaseLogicService<T> baseDaoService;

    public BaseApiController(BaseLogicService<T> baseDaoService) {
        this.baseDaoService = baseDaoService;
    }

    @ApiOperation(value = "添加对象")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response save(@ApiParam(value = "entity对象", name = "object", required = true) @RequestBody T object) {
        return ResponseUtil.ok(baseDaoService.save(object));
    }

    @ApiOperation(value = "更新对象")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@ApiParam(value = "entity对象", name = "object", required = true) @RequestBody T object) {
        return ResponseUtil.ok(baseDaoService.update(object));
    }

    @ApiOperation(value = "根据Id查询对象")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Response getById(@ApiParam(value = "entity对象Id", name = "id", required = true) @RequestParam Integer id) {
        return ResponseUtil.ok(baseDaoService.getById(id));
    }
}