package com.lv_miaoshaProject.dao;

import com.lv_miaoshaProject.dataobject.SequenceInfo;

public interface SequenceInfoMapper {
    int deleteByPrimaryKey(String name);

    int insert(SequenceInfo record);

    int insertSelective(SequenceInfo record);

    SequenceInfo selectByPrimaryKey(String name);

    SequenceInfo getSequenceByName(String name);

    int updateByPrimaryKeySelective(SequenceInfo record);

    int updateByPrimaryKey(SequenceInfo record);
}