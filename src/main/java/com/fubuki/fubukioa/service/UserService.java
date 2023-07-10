package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.User;
import com.fubuki.fubukioa.mapper.UserMapper;
import com.fubuki.fubukioa.service.exception.LoginException;

public class UserService {
    UserMapper userMapper=new UserMapper();

    /**
     * 根据前台输入进行登录校验
     * @param username
     * @param password
     * @return 校验通过后，包含对应用户数据的User实体类
     * @throws LoginException 用户登录异常
     */
    public User checkLogin(String username,String password){
        User user= userMapper.SelectByUserName(username);
        if(user==null){
            throw new LoginException("用户名不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new LoginException("密码错误");
        }
            return user;

    }
}
