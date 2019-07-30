package com.lv_miaoshaProject.controller;

import com.lv_miaoshaProject.common.controller.BaseApiController;
import com.lv_miaoshaProject.common.enmus.ServiceErrorCode;
import com.lv_miaoshaProject.common.response.Response;
import com.lv_miaoshaProject.common.utils.ResponseUtil;
import com.lv_miaoshaProject.dataobject.UserDO;
import com.lv_miaoshaProject.dataobject.vo.Login;
import com.lv_miaoshaProject.dataobject.vo.Register;
import com.lv_miaoshaProject.dataobject.vo.UserVO;
import com.lv_miaoshaProject.service.UserService;
import com.lv_miaoshaProject.service.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * 用户控制层
 *
 */
@Api(value = "user",description = "用户模块")
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")// 解决跨域问题,跨域请求接收
public class UserController extends BaseApiController<UserDO> {

    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public UserController(@Autowired UserService userService) {
        super(userService);
        this.userService = userService;
    }

    // 用户获取OTP短信接口
    @ApiOperation(value="用户获取OTP短信接口")
    @RequestMapping(value="/getOtp",method = RequestMethod.GET)
    public Response getOtp(@RequestParam(name = "telphone")String telphone){
        // 按照一定的规则生成OTP验证码
        Random random =  new Random();
        int randomInt = random.nextInt(99999);
        randomInt+=10000;
        String otpCode = String.valueOf(randomInt);
        // 将OTP验证码同对应的用户的手机号关联，使用httpsession的方式绑定他的手机号
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        // 将OTP验证码通过短信通道发送给用户，此处省略......
        System.out.println("----------------------"+httpServletRequest.getSession().getAttribute(telphone));
        return ResponseUtil.ok(otpCode);
    }


    /**
     * 用户注册
     *
     * @param register
     * @return
     */
    @ApiOperation(value = "用户注册", httpMethod = "POST")
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public Response register(@RequestBody Register register) {
        // 验证手机号和otpCode相符合
        String sessionOtpCode = (String)httpServletRequest.getSession().getAttribute(register.getTelphone());
        if(!com.alibaba.druid.util.StringUtils.equals(register.getOtpCode(),sessionOtpCode)){
            return ResponseUtil.error("短信验证码不符合!");
        }
        userService.register(register);
        return ResponseUtil.ok(null, "注册成功");
    }

    /**
     * 用户登录
     *
     * @param login
     * @return
     */
    @ApiOperation(value = "登录", httpMethod = "POST")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public Response login(@RequestBody Login login) {
        if (StringUtils.isBlank(login.getTelPhone())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        if (StringUtils.isBlank(login.getPassword())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        UserModel returnUser = userService.login(login);
        // 记录登录凭证
        if(returnUser != null){
            httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
            httpServletRequest.getSession().setAttribute("LOGIN_USER",returnUser);
        }
        return ResponseUtil.ok();
    }


    /**
     * 通过用户id获取用户信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value="通过用户id获取用户信息")
    @RequestMapping(value="/getUserById",method = RequestMethod.GET)
    public Response getUserById(@RequestParam(name = "id")Integer id){
        UserModel userModel = userService.getUserById(id);
        return ResponseUtil.ok(converFromModel(userModel));
    }

    // 可供UI调用显示的VO
    private UserVO converFromModel(UserModel userModel) {
        if(userModel == null){
            return null;
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(userModel,vo);
        return vo;
    }


    /**
     * Redis-Token登录
     *
     * @param login
     * @return
     */
    @ApiOperation(value = "Redis-Token登录", httpMethod = "POST")
    @RequestMapping(value = "/doLogin", method = {RequestMethod.POST})
    public Response doLogin(@RequestBody Login login,HttpServletResponse response, HttpServletRequest request) {
        if (StringUtils.isBlank(login.getTelPhone())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        if (StringUtils.isBlank(login.getPassword())) {
            return ResponseUtil.error(ServiceErrorCode.WRONG_DATA);
        }
        return ResponseUtil.ok(userService.doLogin(login,response,request));
    }

    /**
     * 用户注销
     *
     * @param token
     * @return
     */
    @ApiOperation(value = "用户注销", httpMethod = "POST")
    @RequestMapping(value = "/loginOut", method = {RequestMethod.POST})
    public Response loginOut(
            @ApiParam(value = "token", name = "token")
            @RequestParam(name = "token")String token) {
        String result = userService.loginOut(token);
        if(!"1".equals(result)){
            return ResponseUtil.error(result);
        }
        return ResponseUtil.ok();
    }

    /**
     * 通过用户id获取菜单权限列表
     *
     * @param user_id
     * @return
     */
    @ApiOperation(value="通过用户id获取用户信息")
    @RequestMapping(value="/getPrivileges",method = RequestMethod.GET)
    public Response getPrivileges(
            @ApiParam(value = "user_id", name = "user_id")
            @RequestParam(name = "user_id")Integer user_id){
        return ResponseUtil.ok(userService.getPrivileges(user_id));
    }

    /**
     * 通过用户id获取角色名称列表
     *
     * @param user_id
     * @return
     */
    @ApiOperation(value="通过用户id获取角色名称列表")
    @RequestMapping(value="/findRoleNameByUserId",method = RequestMethod.GET)
    public Response findRoleNameByUserId(
            @ApiParam(value = "user_id", name = "user_id")
            @RequestParam(name = "user_id")Integer user_id){
        return ResponseUtil.ok(userService.findRoleNameByUserId(user_id));
    }

    /**
     * 通过用户id获取权限Id列表
     *
     * @param user_id
     * @return
     */
    @ApiOperation(value="通过用户id获取权限Id列表")
    @RequestMapping(value="/findPrivilegeIdsByUserId",method = RequestMethod.GET)
    public Response findPrivilegeIdsByUserId(
            @ApiParam(value = "user_id", name = "user_id")
            @RequestParam(name = "user_id")Integer user_id){
        return ResponseUtil.ok(userService.findPrivilegeIdsByUserId(user_id));
    }

}
