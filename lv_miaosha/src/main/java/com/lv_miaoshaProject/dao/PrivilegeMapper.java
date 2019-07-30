package com.lv_miaoshaProject.dao;

import com.lv_miaoshaProject.dataobject.Privilege;

import java.util.Set;

public interface PrivilegeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Privilege record);

    int insertSelective(Privilege record);

    Privilege selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Privilege record);

    int updateByPrimaryKey(Privilege record);

    Set<Privilege> getPrivilegesByUserId(Integer user_id);
}