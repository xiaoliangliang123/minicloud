package com.minicloud.common.core.util;


import lombok.Data;

import java.io.Serializable;

import static com.minicloud.common.constant.MiniCloudCommonConstant.OptionConstant.SUCCESS;
import static com.minicloud.common.constant.MiniCloudCommonConstant.OptionConstant.FAIL;


@Data
public class ResponseX<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    private int code;

    private String msg;

    private T data;

    public static <T> ResponseX<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> ResponseX<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> ResponseX<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> ResponseX<T> failed() {
        return restResult(null, FAIL, null);
    }

    public static <T> ResponseX<T> failed(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> ResponseX<T> failed(T data) {
        return restResult(data,FAIL, null);
    }

    public static <T> ResponseX<T> failed(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    private static <T> ResponseX<T> restResult(T data, int code, String msg) {
        ResponseX<T> apiResult = new ResponseX<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
