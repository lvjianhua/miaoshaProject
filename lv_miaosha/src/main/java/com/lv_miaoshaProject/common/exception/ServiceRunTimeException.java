package com.lv_miaoshaProject.common.exception;

import com.lv_miaoshaProject.common.enmus.ErrorCodeInterface;

/**
 * 程序运行过程中抛出但未被捕获的异常
 *
 */
public class ServiceRunTimeException  extends RuntimeException{

    private ErrorCodeInterface errorCode;

    private int code;

    /**
     * 自定义业务层异常
     */
    private static final long serialVersionUID = 3297894301127542570L;

    public ServiceRunTimeException() {
        super();
    }

    public ServiceRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceRunTimeException(String message) {
        super(message);
    }

    public ServiceRunTimeException(Throwable cause) {
        super(cause);
    }

    public ServiceRunTimeException(String message, int code) {
        super(message);
        this.code = code;
    }
    public ServiceRunTimeException(ErrorCodeInterface errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCodeInterface getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodeInterface errorCode) {
        this.errorCode = errorCode;
    }
}
