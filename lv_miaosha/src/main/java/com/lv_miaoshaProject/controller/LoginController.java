package com.lv_miaoshaProject.controller;

import com.lv_miaoshaProject.common.config.shiro.MyShiroRealm;
import com.lv_miaoshaProject.common.utils.ShaUtils;
import com.lv_miaoshaProject.dao.UserDOMapper;
import com.lv_miaoshaProject.dataobject.UserDO;
import com.lv_miaoshaProject.dataobject.vo.Login;
import com.lv_miaoshaProject.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Shiro登录跳转控制层
 *
 */
@Controller("auth")
@RequestMapping(value = "/auth")
@Slf4j
public class LoginController {
    @Autowired
    UserDOMapper userDOMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 跳转登录页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public ModelAndView login(ModelAndView model) {
        if (model == null) {
            new ModelAndView();
        }
        model.setViewName("login");
        return model;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/doLogin", method = {RequestMethod.POST})
    @ResponseBody
    public HashMap<String,Object> doLogin(@RequestParam(value="username")String username,
                          @RequestParam(value="password")String password,
                            HttpServletResponse response,
                            HttpServletRequest request,
                          Model model) throws Exception{
        log.info("----------进行登录---------");
        HashMap<String,Object> data = new HashMap();
        // 封装登录Login
        Login login = new Login();
        login.setTelPhone(username);
        login.setPassword(password);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            data.put("code",0);
            data.put("message","用户名或密码不能为空!");
            return data;
        }
        // 使用用户的登录信息创建令牌,token可以理解为用户令牌，登录的过程被抽象为Shiro验证令牌是否具有合法身份以及相关权限。
        // 注:密码不能使用明文传输
        UsernamePasswordToken token = new UsernamePasswordToken(username, ShaUtils.sha512(password));
        // 获取Subject单例对象
        Subject subject = SecurityUtils.getSubject();
        try {
            // 用户登录验证
            subject.login(token);
            data.put("code",1);
            data.put("url","/home");
            data.put("message","登陆成功");
            log.info(username+"登陆成功");
        }
        catch (UnknownAccountException e) {
            data.put("code",0);
            data.put("message",username+"账号不存在");
            log.error(username+"账号不存在");
            return data;
        }catch (DisabledAccountException e){
            data.put("code",0);
            data.put("message",username+"账号异常");
            log.error(username+"账号异常");
            return data;
        }catch (AuthenticationException se) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            log.info("===============Shiro用户认证失败,错误原因." + se.getMessage());
            token.clear();
            data.put("code",0);
            data.put("message",se.getMessage());
            return data;
        }
        log.info("===============Shiro初始化用户信息到session");
        UserDO userDO = userDOMapper.selectByTelPhone(username);
        Session session = subject.getSession();
        session.setAttribute("MEMBER_USER_KEY", userDO.getId());
        model.addAttribute("hashMap",userDO);
        return data;
    }

    /**
     * 用户注销(清除redis缓存)
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response,ModelAndView model) {
        // 清除当前用户的redis缓存
        log.info("===============退出当前用户,清除当前用户的信息");
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm = (MyShiroRealm) securityManager.getRealms().iterator().next();
        realm.clearCache(SecurityUtils.getSubject().getPrincipals());
        SecurityUtils.getSubject().logout();
        model.setViewName("login");
        return model;
    }

}
