package com.wong.common.model.dto;

import lombok.Data;

/**
 * 登录与认证请求
 *
 * @author Wongbuer
 */
@Data
public class LoginAndAuthenticateRequest {
    private String idNumber;
    private String admissionNumber;
    private String faceImgBase64;
}
