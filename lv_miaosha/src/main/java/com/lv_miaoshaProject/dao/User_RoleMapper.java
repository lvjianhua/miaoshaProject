package com.lv_miaoshaProject.dao;

import com.lv_miaoshaProject.dataobject.User_Role;

import java.util.Set;

public interface User_RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_Role record);

    int insertSelective(User_Role record);

    User_Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_Role record);

    int updateByPrimaryKey(User_Role record);
}