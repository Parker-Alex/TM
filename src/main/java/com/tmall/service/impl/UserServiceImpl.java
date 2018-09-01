package com.tmall.service.impl;

import com.tmall.dao.UserMapper;
import com.tmall.pojo.User;
import com.tmall.pojo.UserExample;
import com.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean isExist(String name) {
        UserExample example = new UserExample();
        example.or().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
        return users.isEmpty();
    }

    @Override
    public User getByNameAndPw(String name, String password) {
        UserExample example = new UserExample();
        example.or().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(example);
        if (users.isEmpty())
            return null;
        return users.get(0);
    }
}
