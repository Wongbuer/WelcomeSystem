package com.wong.usersystem.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.common.model.entity.User;
import com.wong.common.utils.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * (User)表服务接口
 *
 * @author Wongbuer
 * @since 2024-03-08 23:17:12
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     *
     * @param user 用户实体
     * @return 登录结果
     */
    CommonResponse<?> login(User user);

    /**
     * 登录并认证
     *
     * @return
     */
    CommonResponse<User> loginAndAuthentication(MultipartFile multipartFile, User user);
}

