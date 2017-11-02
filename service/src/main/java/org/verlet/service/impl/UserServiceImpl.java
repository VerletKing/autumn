package org.verlet.service.impl;

import org.springframework.stereotype.Service;
import org.verlet.service.UserService;

@Service
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
public class UserServiceImpl implements UserService {


    @Override
    public void show() {
        System.out.println("hello world");
    }
}
