package com.wong.common.utils;

import org.springframework.http.HttpStatus;

/**
 * @author Wongbuer
 */
public class ResultUtil {
    public static String DEFAULT_MSG = "success";

    public static <T> CommonResponse<T> build(Integer code, String msg, T data) {
        return new CommonResponse<T>(code, msg, data);
    }

    public static <T> CommonResponse<T> build(Integer code, String msg) {
        return new CommonResponse<T>(code, msg);
    }

    public static <T> CommonResponse<T> success(String msg) {
        return new CommonResponse<T>(200, msg);
    }

    public static <T> CommonResponse<T> success(int code, String msg) {
        return new CommonResponse<T>(code, msg);
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<T>(200, DEFAULT_MSG, data);
    }

    public static <T> CommonResponse<T> success(String msg, T data) {
        return new CommonResponse<T>(msg, data);
    }

    public static <T> CommonResponse<T> success(HttpStatus status, T data) {
        return new CommonResponse<T>(status.value(), DEFAULT_MSG, data);
    }

    public static <T> CommonResponse<T> success(HttpStatus status) {
        return new CommonResponse<T>(status.value(), DEFAULT_MSG);
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<T>("success");
    }

    public static <T> CommonResponse<T> error(Integer code, String msg) {
        return new CommonResponse<>(code, msg);
    }

    public static <T> CommonResponse<T> error(HttpStatus status, String msg) {
        return new CommonResponse<>(status.value(), msg);
    }
}
