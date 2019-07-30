package com.lv_miaoshaProject.dao;

import com.lv_miaoshaProject.dataobject.Privilege_Role;

import java.util.Set;

public interface Privilege_RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Privilege_Role record);

    int insertSelective(Privilege_Role record);

    Privilege_Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Privilege_Role record);

    int updateByPrimaryKey(Privilege_Role record);

    Set<String> findPrivilegeIdsByUserId(Integer user_id);
}