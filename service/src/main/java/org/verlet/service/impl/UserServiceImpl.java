package org.verlet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.verlet.dao.UserInfoMapper;
import org.verlet.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public String login(String account, String password) {
        return userInfoMapper.login(account, password);
    }
}
