package com.lv_miaoshaProject.service;

import com.lv_miaoshaProject.common.base.BaseLogicService;
import com.lv_miaoshaProject.dataobject.Privilege;
import com.lv_miaoshaProject.dataobject.UserDO;
import com.lv_miaoshaProject.dataobject.vo.Login;
import com.lv_miaoshaProject.dataobject.vo.Register;
import com.lv_miaoshaProject.service.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Set;

/**
 * 用户接口
 */
public interface UserService extends BaseLogicService<UserDO> {
    // 通过用户id获取用户对象
    UserModel getUserById(Integer id);

    // 用户注册
    void register(Register register);

    // 用户登录
    UserModel login(Login login);

    // 用户登陆-Redis-token
    HashMap doLogin(Login login, HttpServletResponse response, HttpServletRequest request);

    // 用户注销
    String loginOut(String token);

    // 根据用户id获取权限列表
    Set<Privilege> getPrivileges(Integer user_id);

    // 根据用户id获取角色名称列表
    Set<String> findRoleNameByUserId(Integer user_id);

    // 根据用户id获取权限id列表
    Set<String> findPrivilegeIdsByUserId(Integer user_id);
}
