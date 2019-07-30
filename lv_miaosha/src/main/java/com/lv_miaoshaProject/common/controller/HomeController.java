package com.lv_miaoshaProject.common.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class HomeController {

    /**
     * 登录时的链接
     *
     * @param mv
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mv) {
        if (mv == null) {
            new ModelAndView();
        }
        // 获取shiro中的session
        Session session = SecurityUtils.getSubject().getSession();
        if (session.getAttribute("MEMBER_USER_KEY") == null) {
            log.info("用户没有登录,即将跳转登录页面");
            mv.setViewName("login");
        } else {
            log.info("用户已经登录,即将跳转登录页面");
            mv.setViewName("home");
        }
        return mv;
    }

    /**
     * 用户登录后的首页
     *
     * @return
     */
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        return mv;
    }

}