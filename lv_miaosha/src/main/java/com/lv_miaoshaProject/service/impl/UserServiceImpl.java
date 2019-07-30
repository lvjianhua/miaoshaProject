package com.lv_miaoshaProject.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lv_miaoshaProject.common.base.Constants;
import com.lv_miaoshaProject.common.enmus.ServiceErrorCode;
import com.lv_miaoshaProject.common.exception.ServiceRunTimeException;
import com.lv_miaoshaProject.common.utils.CookieUtil;
import com.lv_miaoshaProject.common.utils.ShaUtils;
import com.lv_miaoshaProject.common.utils.TokenUtils;
import com.lv_miaoshaProject.dao.*;
import com.lv_miaoshaProject.dataobject.Privilege;
import com.lv_miaoshaProject.dataobject.UserDO;
import com.lv_miaoshaProject.dataobject.User_PasswordDO;
import com.lv_miaoshaProject.dataobject.vo.Login;
import com.lv_miaoshaProject.dataobject.vo.Register;
import com.lv_miaoshaProject.service.RedisService;
import com.lv_miaoshaProject.service.UserService;
import com.lv_miaoshaProject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 接口实现类
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private User_PasswordDOMapper userPasswordDOMapper;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private Privilege_RoleMapper privilegeRoleMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null){
            throw new ServiceRunTimeException(ServiceErrorCode.NO_USER);
        }
        User_PasswordDO user_passwordDO = userPasswordDOMapper.selectByUserId(id);
        UserModel userModel = pageUserVO(userDO,user_passwordDO);
        return userModel;
    }

    @Override
    @Transactional
    public void register(Register register) {
        if(register == null){
            throw new ServiceRunTimeException(ServiceErrorCode.OBJECT_IS_NULL);
        }
        if(StringUtils.isEmpty(register.getName())
                || register.getGender() == null
                || register.getAge() == null
                || StringUtils.isEmpty(register.getOtpCode())
                || StringUtils.isEmpty(register.getTelphone())
                ){
            throw new ServiceRunTimeException(ServiceErrorCode.WRONG_DATA);
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(register,userDO);
        // 捕获telphone唯一索引异常
        try{
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException ex){
            throw new ServiceRunTimeException(ServiceErrorCode.REPEAT_PHONE);
        }
        User_PasswordDO user_passwordDO = new User_PasswordDO();
        user_passwordDO.setUserId(userDO.getId());
        user_passwordDO.setEncrptPassword(ShaUtils.sha512(register.getEncrptPassword()));
        userPasswordDOMapper.insertSelective(user_passwordDO);
    }

    @Override
    public UserModel login(Login login) {
        UserDO ud = userDOMapper.selectByTelPhone(login.getTelPhone());
        if(ud == null){
            throw new ServiceRunTimeException(ServiceErrorCode.LOGIN_ERROR);
        }
        User_PasswordDO up = userPasswordDOMapper.selectByUserId(ud.getId());
        UserModel userModel =  pageUserVO(ud,up);
        if(!StringUtils.equals(ShaUtils.sha512(login.getPassword()),up.getEncrptPassword())){
            throw new ServiceRunTimeException(ServiceErrorCode.LOGIN_ERROR);
        }
        return userModel;
    }

    @Override
    public HashMap doLogin(Login login,HttpServletResponse response, HttpServletRequest request) throws ServiceRunTimeException{
        HashMap<String,Object> map = new HashMap<>();
        UserDO user = userDOMapper.selectByTelPhone(login.getTelPhone());
        if(ObjectUtils.isEmpty(user)){
            throw new ServiceRunTimeException(ServiceErrorCode.LOGIN_ERROR);
        }
        User_PasswordDO up = userPasswordDOMapper.selectByUserId(user.getId());
        if(ObjectUtils.isEmpty(up)){
            throw new ServiceRunTimeException(ServiceErrorCode.LOGIN_ERROR);
        }
        UserModel userModel = pageUserVO(user,up);
        if(!StringUtils.equals(ShaUtils.sha512(login.getPassword()),up.getEncrptPassword())){
            throw new ServiceRunTimeException(ServiceErrorCode.LOGIN_ERROR);
        }
        String oldToken = null;
        Cookie cookie = CookieUtil.getCookieByName(request,"LVJHTOKEN");
        if(cookie != null){
            oldToken = cookie.getValue();
        }
        String token = null;
        if(!ObjectUtils.isEmpty(userModel)){
            if(!ObjectUtils.isEmpty(redisService.get(String.valueOf(oldToken)))){
                redisService.del(oldToken);
            }
            // 生成token
            token = Constants.tokenPrefix+TokenUtils.createToken(String.valueOf(userModel.getId()),userModel.getTelphone());
            // 写入缓存redis并设置过期时间
            redisService.set(token,JSON.toJSON(userModel));
            redisService.expire(token,Constants.loginExpire);
            // 发送cookie至客户端
            String domain = ".jszx.com";
            Integer maxAge = 1800000;
            CookieUtil.addCookie(response,"LVJHTOKEN",token,domain,maxAge);
            // 登录成功返回用户信息和token
            map.put("User",user);
            map.put("token",token);
        }else{
            throw new ServiceRunTimeException(ServiceErrorCode.LOGIN_FAIL);
        }
        // 测试token
        //createToken();
        return map;
    }

    private void createToken() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(9);
        ids.add(10);
        ids.add(11);
        ids.add(12);
        ids.add(13);
        ids.add(14);
        ids.add(15);
        ids.add(16);
        ids.add(17);
        ids.add(18);
        ids.add(19);
        ids.add(20);
        ids.add(21);
        ids.add(22);
        ids.add(23);
        for(Integer id:ids){
            UserModel userModel = getUserById(id);
            if(!ObjectUtils.isEmpty(userModel)){
                redisService.set(Constants.tokenPrefix+id,JSON.toJSON(userModel));
            }
        }
    }


    @Override
    public String loginOut(String token) {
        String result = (String)redisService.get(token);
        if(!ObjectUtils.isEmpty(result)){
            redisService.del(token);
        }else{
            throw new ServiceRunTimeException(ServiceErrorCode.NO_LOGIN);
        }
        return "1";
    }

    @Override
    public Set<Privilege> getPrivileges(Integer user_id) {
        return privilegeMapper.getPrivilegesByUserId(user_id);
    }

    @Override
    public Set<String> findRoleNameByUserId(Integer user_id) {
        return roleMapper.findRoleNameByUserId(user_id);
    }

    @Override
    public Set<String> findPrivilegeIdsByUserId(Integer user_id) {
        return privilegeRoleMapper.findPrivilegeIdsByUserId(user_id);
    }

    public static UserModel pageUserVO(UserDO userDO, User_PasswordDO user_passwordDO) {
        if(userDO == null){
            return null;
        }
        UserModel model = new UserModel();
        BeanUtils.copyProperties(userDO,model);
        if(user_passwordDO != null){
            model.setEncrptPassword(user_passwordDO.getEncrptPassword());
        }
        return model;
    }

    @Override
    public UserDO getById(Integer id) {
        return userDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(UserDO object) {
        return userDOMapper.updateByPrimaryKey(object);
    }

    @Override
    public int save(UserDO object) {
        return userDOMapper.insertSelective(object);
    }
}


