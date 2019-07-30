package com.lv_miaoshaProject.common.utils;

import com.lv_miaoshaProject.common.enmus.ErrorCode;
import com.lv_miaoshaProject.common.enmus.ErrorCodeInterface;
import com.lv_miaoshaProject.common.response.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ResponseUtil {

    public static <T> Response<T> getResponse() {
        Response<T> response = new Response<T>();
        return response;
    }

    /**
     * 正确返回
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> ok() {
        return ok((T) null);
    }


    /**
     * list ok
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<Collection<T>> ok(Collection<T> data) {
        Response<Collection<T>> response = null;
        if (data != null) {
            response = new Response<Collection<T>>(data, ErrorCode.SUCCESS);
        } else {
            response = new Response<Collection<T>>(new ArrayList<T>(), ErrorCode.SUCCESS);
        }
        return response;
    }

    /**
     * int类型ok
     *
     * @param data
     * @return
     */
    public static Response<Number> ok(Number data) {
        Response<Number> response = null;
        if (data != null) {
            response = new Response<Number>(data, ErrorCode.SUCCESS);
        } else {
            response = new Response<Number>(0, ErrorCode.SUCCESS);
        }
        return response;
    }

    /**
     * String 类型ok
     *
     * @param data
     * @return
     */
    public static Response<String> ok(String data) {
        Response<String> response = null;
        if (data != null) {
            response = new Response<String>(data, ErrorCode.SUCCESS);
        } else {
            response = new Response<String>("", ErrorCode.SUCCESS);
        }
        return response;
    }

    /**
     * String 类型ok
     *
     * @param data
     * @return
     */
    public static Response<Boolean> ok(Boolean data) {
        Response<Boolean> response = null;
        if (data != null) {
            response = new Response<Boolean>(data, ErrorCode.SUCCESS);
        } else {
            response = new Response<Boolean>(false, ErrorCode.SUCCESS);
        }
        return response;
    }


    public static <T> Response<T> ok(T data) {
        Response<T> response = null;
        if (data != null) {
            response = new Response<T>(data, ErrorCode.SUCCESS);
        } else {
            response = new Response<T>(null, ErrorCode.SUCCESS);
        }
        return response;
    }

    public static <T> Response<T> ok(T data, String mes) {
        Response<T> response = null;
        if (data != null) {
            response = new Response<T>(data, mes);
        } else {
            response = new Response<T>(null, mes);
            response.setData((T) new HashMap());
        }
        return response;

    }

    /**
     * 错误返回
     *
     * @return
     */
    public static <T> Response<T> error() {
        Response<T> response = new Response<T>(null);
        return response;
    }

    public static <T> Response<T> error(String message) {
        Response<T> response = new Response<T>(message);
        return response;
    }

    public static <T> Response<T> error(ErrorCodeInterface errorCode) {
        return error(errorCode, null);
    }

    public static <T> Response<T> error(ErrorCodeInterface errorCode, Object key, Object value) {
        if (key == null) {
            return error(errorCode);
        }
        if (value == null) {
            value = "";
        }
        return (Response<T>) error(errorCode, String.format("%s:%s", key, value));
    }

    public static <T> Response<T> error(ErrorCodeInterface errorCode, T data) {
        if (errorCode == null) {
            errorCode = ErrorCode.ERROR;
        }
        return new Response<T>(data, errorCode);
    }

}
