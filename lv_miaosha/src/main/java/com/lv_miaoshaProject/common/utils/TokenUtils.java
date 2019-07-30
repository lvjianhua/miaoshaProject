package com.lv_miaoshaProject.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成token工具类
 *
 */
public class TokenUtils {
    /**
     * 创建token
     *
     * @param prifix：头
     * @param tail：尾
     * @return
     */
    public static String createToken(String prifix,String tail) {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String timeStamp = format.format(new Date());
        String soruce = prifix+tail+timeStamp;
        String token = MD5Utils.getPwd(soruce);
        return token;
    }
}
