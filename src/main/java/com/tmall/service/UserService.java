package com.tmall.service;

import com.tmall.pojo.User;

public interface UserService {

    void addUser(User user);

    User getUser(Integer id);

    void deleteUser(Integer id);

    void updateUser(User user);

    boolean isExist(String name);

    User getByNameAndPw(String name, String password);
}
