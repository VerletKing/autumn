package org.verlet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.verlet.dao.UserMapper;
import org.verlet.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public String login(String account, String password) {
        return userMapper.login(account,password);
    }
}
