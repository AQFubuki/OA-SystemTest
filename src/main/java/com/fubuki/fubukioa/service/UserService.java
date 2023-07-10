package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.User;
import com.fubuki.fubukioa.mapper.UserMapper;
import com.fubuki.fubukioa.service.exception.LoginException;
import com.fubuki.fubukioa.utils.MD5Utils;

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
        //使用md5+盐值对密码进行处理
        String md5= MD5Utils.md5Digest(password, user.getSalt());
        if (!user.getPassword().equals(md5)) {
            throw new LoginException("密码错误");
        }
            return user;
    }
}
