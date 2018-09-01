package com.tmall.service;

import com.tmall.BaseTest;
import com.tmall.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        User user = new User();
        user.setName("测试1");
        userService.addUser(user);
    }
}