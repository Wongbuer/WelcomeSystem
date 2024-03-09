package com.wong.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Wongbuer
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    public CommonResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResponse(String msg) {
        this.msg = msg;
    }

    public CommonResponse(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public CommonResponse(T data) {
        this.data = data;
    }

    public CommonResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResponse() {
    }
}
