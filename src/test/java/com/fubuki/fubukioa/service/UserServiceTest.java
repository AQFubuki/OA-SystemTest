package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.User;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    UserService userService=new UserService();

    @Test
    void checkLogin1() {
        User user=userService.checkLogin("test","test");
        System.out.println(user);
    }

    @Test
    void checkLogin2() {
        userService.checkLogin("test111", "test");
    }

    @Test
    void checkLogin3() {
        userService.checkLogin("m8", "test");
    }

    @Test
    void checkLogin() {
    }
}