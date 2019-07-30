package com.lv_miaoshaProject.common.config.shiro;

import com.alibaba.druid.util.StringUtils;
import com.lv_miaoshaProject.dao.UserDOMapper;
import com.lv_miaoshaProject.dao.User_PasswordDOMapper;
import com.lv_miaoshaProject.dataobject.UserDO;
import com.lv_miaoshaProject.dataobject.User_PasswordDO;
import com.lv_miaoshaProject.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 自定义Realm
 *
 * @author Lv
 */
public class MyShiroRealm extends AuthorizingRealm {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    //如果项目中用到了事物，@Autowired注解会使事物失效，可以自己用get方法获取值
    @Autowired
    private UserService userService;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private User_PasswordDOMapper userPasswordDOMapper;

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());

        // 从数据库获取对应用户名密码的用户
        UserDO user = userDOMapper.selectByTelPhone(name);
        if(user == null){
            throw new AuthenticationException("用户名或密码有误！");
        }
        User_PasswordDO up = userPasswordDOMapper.selectByUserId(user.getId());
        if(!StringUtils.equals(password,up.getEncrptPassword())){
            throw new AuthenticationException("用户名或密码有误！");
        }
        if (user != null) {
            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user, //用户
                    password, //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        }
        throw new UnknownAccountException();
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (principal instanceof UserDO) {
            UserDO userLogin = (UserDO) principal;
            Set<String> roles = userService.findRoleNameByUserId(userLogin.getId());
            authorizationInfo.addRoles(roles);

            Set<String> permissions = userService.findPrivilegeIdsByUserId(userLogin.getId());
            authorizationInfo.addStringPermissions(permissions);
        }
        logger.info("---- 获取到以下权限 ----");
        logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return authorizationInfo;
    }

    /**
     * 清除当前用户的权限认证缓存
     *
     * @param principals 权限信息
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}
