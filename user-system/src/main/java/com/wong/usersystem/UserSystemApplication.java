package com.wong.usersystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Wongbuer
 */
@SpringBootApplication
@MapperScan("com.wong.usersystem.mapper")
@ComponentScan({"com.wong.usersystem", "com.wong.common"})
public class UserSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserSystemApplication.class, args);
    }

}
