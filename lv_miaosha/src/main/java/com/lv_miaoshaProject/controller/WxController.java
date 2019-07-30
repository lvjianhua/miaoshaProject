package com.lv_miaoshaProject.controller;

import com.alibaba.fastjson.JSONObject;
import com.lv_miaoshaProject.common.config.wx.WxConfig;
import com.lv_miaoshaProject.common.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 第三方微信登录接入
 *
 */
@Controller
@RequestMapping(value="/api")
public class WxController {

    @Autowired
    private WxConfig wxConfig;

    /**
     * 让用户进行授权
     *
     * @return
     */
    @RequestMapping(value="/wx/dologin")
    public String toWxLogin() throws Exception {
        return "redirect:"+wxConfig.reqCodeUrl();
    }

    /**
     * 微信端回调
     *
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping("/wx/callBack")
    public String callBack(String code)throws Exception{
        // 获取accessToken的json串
        String accessTokenjsonStr = UrlUtils.loadURL(wxConfig.reqAccessTokenUrl(code));
        JSONObject accessTokenjson = JSONObject.parseObject(accessTokenjsonStr);
        String accessToken = accessTokenjson.getString("access_token");
        // 用户唯一标识
        String openId = accessTokenjson.getString("openid");
        // 获取用户信息
        String userInfoJsonStr = UrlUtils.loadURL(wxConfig.reqUserInfoUrl(accessToken,openId));
        String token = null;//localUserService.createWxUserToken(userInfoJsonStr);
        // 跳转至前端首页
        return "redirect:"+wxConfig.getSuccessUrl()+"?token="+token;
    }
}
