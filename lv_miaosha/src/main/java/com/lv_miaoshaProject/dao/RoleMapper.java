package com.lv_miaoshaProject.dao;

import com.lv_miaoshaProject.dataobject.Role;

import java.util.Set;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    Set<String> findRoleNameByUserId(Integer user_id);
}