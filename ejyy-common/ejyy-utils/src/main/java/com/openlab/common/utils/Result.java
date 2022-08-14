package com.openlab.common.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回统一结果的类
 */
@Data
public final class Result {
    private int code; // 状态码
    private boolean success; // 是否成功的标识
    private String message; // 结果提示信息
    private Map<String, Object> data = new HashMap<>(); // 结果数据集

    // 把构造方法私有化
    private Result(){}

    // 成功的静态方法
    public static Result ok() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }

    public static Result ok(int code, Map<String, Object> data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(code);
        result.setData(data);
        return result;
    }

    public static Result ok(int code, String message, Map<String, Object> data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result ok(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    // 只设置标识
    public static Result success(boolean success) {
        Result result = new Result();
        result.setSuccess(success);
        return result;
    }


    // 只设置状态码
    public static Result code(int code) {
        Result result = new Result();
        result.setCode(code);
        return result;
    }

    // 失败静态方法
    public static Result error() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(20001);
        result.setMessage("失败");
        return result;
    }

    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
