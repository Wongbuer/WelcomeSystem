package com.wong.usersystem;

import com.wong.usersystem.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserSystemApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        userService.list().forEach(System.out::println);
    }

}
