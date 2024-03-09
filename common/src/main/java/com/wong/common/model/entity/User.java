package com.wong.common.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wong.common.validate.LoginGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)表实体类
 *
 * @author Wongbuer
 * @since 2024-03-09 16:44:05
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;
    /**
     * 用户名
     */
    @TableField("username")
    @NotBlank(message = "username不能为空")
    @Size(max = 255, message = "username长度不能超过255", groups = LoginGroup.class)
    private String username;
    /**
     * 密码哈希值
     */
    @TableField("password_hash")
    @NotBlank(message = "password不能为空", groups = LoginGroup.class)
    @Size(max = 255, message = "password长度不能超过255")
    private String password;
    /**
     * 电子邮件地址
     */
    @TableField("email")
    @NotBlank(message = "email不能为空")
    @Size(max = 255, message = "email长度不能超过255")
    private String email;
    /**
     * 手机号码
     */
    @TableField("phone")
    @NotBlank(message = "phone不能为空")
    @Size(max = 15, message = "phone长度不能超过15")
    private String phone;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private Date updatedAt;

}

