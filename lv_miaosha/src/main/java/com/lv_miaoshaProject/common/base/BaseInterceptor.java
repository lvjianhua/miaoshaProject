package com.lv_miaoshaProject.common.base;

import com.lv_miaoshaProject.dataobject.Privilege;
import com.lv_miaoshaProject.dataobject.UserDO;
import com.lv_miaoshaProject.dao.PrivilegeMapper;
import netscape.security.PrivilegeManager;

import java.util.Iterator;
import java.util.Set;

/**
 * 权限判断封装类
 *
 * @author Lv
 */
public class BaseInterceptor {

    /**
     * 权限列表查询
     * @param privilegeMapper
     * @param url
     * @return
     */
    public boolean isHavePermiss(PrivilegeMapper privilegeMapper, UserDO userDO, String url){
        boolean falg = false;

        //获取用户权限
        Set<Privilege> permissionInfos = privilegeMapper.getPrivilegesByUserId(userDO.getId());

        //对比查询权限
        Iterator<Privilege> iterator = permissionInfos.iterator();

        while(iterator.hasNext()){
            Privilege permissionInfo = iterator.next();
            if(permissionInfo.getUrl().equals(url)){
                falg = true;
            }
        }
        return falg;
    }
}
