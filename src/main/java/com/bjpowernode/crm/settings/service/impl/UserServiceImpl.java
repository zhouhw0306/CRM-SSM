package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User queryUserByLoginActAndPwd(Map<String, Object> map) {
         return userDao.selectUserByLoginActAndPwd(map);
    }

    @Override
    public List<User> selectUserList() {
        List<User> users = userDao.getUserList();
        return users;
    }

}
