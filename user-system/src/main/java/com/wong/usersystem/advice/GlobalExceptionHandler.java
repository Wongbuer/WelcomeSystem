package com.wong.usersystem.advice;

import com.wong.common.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author Wongbuer
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e)
    {
        log.error("异常信息：", e);
        return new CommonResponse<>(500, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public CommonResponse<?> handleBadCredentialsException(AuthenticationException e)
    {
        log.error("异常信息：", e);
        return new CommonResponse<>(401, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResponse<?> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException e)
    {
        log.error("异常信息：", e);
        return new CommonResponse<>(403, e.getMessage());
    }
}
