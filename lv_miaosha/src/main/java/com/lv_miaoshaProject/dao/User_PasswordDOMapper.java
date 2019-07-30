package com.lv_miaoshaProject.dao;

import com.lv_miaoshaProject.dataobject.User_PasswordDO;

public interface User_PasswordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_PasswordDO record);

    int insertSelective(User_PasswordDO record);

    User_PasswordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_PasswordDO record);

    int updateByPrimaryKey(User_PasswordDO record);

    User_PasswordDO selectByUserId(Integer userId);
}