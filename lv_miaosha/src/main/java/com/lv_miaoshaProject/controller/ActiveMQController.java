package com.lv_miaoshaProject.controller;

import com.lv_miaoshaProject.common.response.Response;
import com.lv_miaoshaProject.common.utils.ActiveMQUtils;
import com.lv_miaoshaProject.common.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ActiveMQ
 *
 */
@RestController
@RequestMapping(value="/message")
public class ActiveMQController {

    @Autowired
    private ActiveMQUtils activeMQUtils;

    /**
     * 发送消息
     *
     * @return
     */
    @RequestMapping(value="/send",method = RequestMethod.POST)
    public Response send(){
        activeMQUtils.sendQueueMessage("ms","is test!");
        return ResponseUtil.ok("发送成功");
    }

    /**
     * 监听并接收消息
     *
     * @param message
     * @return
     */
    @JmsListener(destination = "ms")
    public void recevieMessage(String message){
        System.out.print("message:"+message);

    }
}
