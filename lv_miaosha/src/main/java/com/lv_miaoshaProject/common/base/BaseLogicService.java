package com.lv_miaoshaProject.common.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface BaseLogicService<T> {
    /**
     * 根据id获取对应的对象
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getById")
    public T getById(@RequestParam(value = "id") Integer id);

    /**
     * 更新对应的对象
     *
     * @param object
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public int update(T object);

    /**
     * 保存对象
     *
     * @param object
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public int save(T object);
}

