package com.wong.usersystem.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.common.feign.FaceFeign;
import com.wong.common.model.entity.User;
import com.wong.common.model.face.UserFace;
import com.wong.common.utils.CommonResponse;
import com.wong.common.utils.JwtUtil;
import com.wong.common.utils.ResultUtil;
import com.wong.usersystem.mapper.UserMapper;
import com.wong.usersystem.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

import static com.wong.usersystem.config.SecurityConfig.SECURITY_LOGIN_PREFIX;

/**
 * (User)表服务实现类
 *
 * @author Wongbuer
 * @since 2024-03-08 23:17:12
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private FaceFeign faceFeign;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public CommonResponse<?> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        boolean isMatch = authenticate.isAuthenticated();

        if (isMatch) {
            // 创建token
            String token = JwtUtil.createToken("username", user.getUsername());
            // redis存放user
            String redisKey = SECURITY_LOGIN_PREFIX + user.getUsername();
            redisTemplate.opsForValue().set(redisKey, authenticate.getPrincipal());
            // 返回token
            return ResultUtil.success(token);
        }
        return ResultUtil.error(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
    }

    @Override
    public CommonResponse<User> loginAndAuthentication(MultipartFile multipartFile, User user, String faceImgBase64) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getIdNumber, user.getIdNumber())
                        .eq(User::getAdmissionNumber, user.getAdmissionNumber());
        User dbUser = baseMapper.selectOne(wrapper);
        try {
            if (dbUser != null) {
                UserFace userFace = new UserFace();
                userFace.setUserId(dbUser.getId());
                if (multipartFile != null) {
                    String picString = Base64.getEncoder().encodeToString(multipartFile.getBytes());
                    userFace.setPicString(picString);
                } else if (faceImgBase64 != null) {
                    userFace.setPicString(faceImgBase64);
                }
                CommonResponse<String> response = faceFeign.faceSearchWithBase64(userFace);
                if (response.getCode() == 200) {
                    return ResultUtil.success(dbUser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, "人脸信息错误");
    }
}

