package com.lv_miaoshaProject.common.controller;

import com.lv_miaoshaProject.common.enmus.ErrorCode;
import com.lv_miaoshaProject.common.exception.ServiceRunTimeException;
import com.lv_miaoshaProject.common.response.Response;
import com.lv_miaoshaProject.common.utils.ResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 *
 */
public class BaseController {
    protected final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * 统一异常处理
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Response exceptionHandler(HttpServletRequest request, Exception ex) {
        logger.error("捕获异常,method: " + request.getRequestURI(), ex);
        // 运行时异常
        if (ex instanceof ServiceRunTimeException) {
            if (((ServiceRunTimeException) ex).getErrorCode() != null) {
                ServiceRunTimeException srte = (ServiceRunTimeException) ex;
                return ResponseUtil.error(srte.getErrorCode());
            } else {
                logger.error(ex.getMessage(), ex);
            }
            return ResponseUtil.error(ex.getMessage());
        }

        //  负载均衡异常，负载均衡器没有客户端可用的服务器
      /*  if (ex instanceof HystrixRuntimeException) {
            if (ex.getCause().getMessage().contains("Load balancer does not have available server for client")
                    || ex.getCause().getMessage().contains("Connection refused")) {
                return ResponseUtil.error(ErrorCode.WRONG_ONSERVER);
            }
            return ResponseUtil.error(ex.getCause().getMessage());
        }*/
        return ResponseUtil.error(ErrorCode.WRONG_ONSERVER);
    }
}
