package com.wong.common.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wong.common.validate.AddGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)表实体类
 *
 * @author Wongbuer
 * @since 2024-03-12 19:23:57
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(value = "id")
    @NotBlank(message = "id不能为空")
    @Size(max = 15, message = "id长度不能超过15", groups = {AddGroup.class})
    private String id;
    /**
     * 用户名
     */
    @TableField("username")
    @NotBlank(message = "username不能为空")
    @Size(max = 255, message = "username长度不能超过255", groups = {AddGroup.class})
    private String username;
    /**
     * 密码哈希值
     */
    @TableField("password_hash")
    @NotBlank(message = "password不能为空")
    @Size(max = 255, message = "password长度不能超过255", groups = {AddGroup.class})
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
     * 身份证号
     */
    @TableField("id_number")
    @Pattern(regexp = "^(\\d{15}$)|(^\\d{17}([0-9]|[xX]))$", groups = {AddGroup.class})
    private String idNumber;
    /**
     * 头像
     */
    @TableField("avatar")
    @NotBlank(message = "avatar不能为空")
    @Size(max = 255, message = "avatar长度不能超过255")
    private String avatar;
    /**
     * 昵称
     */
    @TableField("nickname")
    @NotBlank(message = "nickname不能为空")
    @Size(max = 255, message = "nickname长度不能超过255")
    private String nickname;

    @TableField("admission_number")
    private String admissionNumber;
    /**
     * 积分
     */
    @TableField("credit")
    @PositiveOrZero
    private Integer credit;
    /**
     * 是否注册
     */
    @TableField("is_registered")
    private Integer isRegistered;
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
