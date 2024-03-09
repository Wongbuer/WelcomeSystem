DROP DATABASE IF EXISTS `welcome_system`;

CREATE DATABASE IF NOT EXISTS `welcome_system`;

USE `welcome_system`;

-- 用户系统相关表
CREATE TABLE IF NOT EXISTS `user`
(
    `id`                    INT AUTO_INCREMENT PRIMARY KEY,
    `username`              VARCHAR(255) NOT NULL COMMENT '用户名',
    `password_hash`         VARCHAR(255) NOT NULL COMMENT '密码哈希值',
    `email`                 VARCHAR(255) UNIQUE COMMENT '电子邮件地址',
    `phone`                 VARCHAR(15) UNIQUE COMMENT '手机号码',
    `created_at`            TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`            TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);