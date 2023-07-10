package com.fubuki.fubukioa.mapper;

import com.fubuki.fubukioa.entity.User;
import com.fubuki.fubukioa.utils.MyBatisUtils;

public class UserMapper {
    public User SelectByUserName(String username){
        User user =(User) MyBatisUtils.executeQuery(
                sqlSession -> sqlSession.selectOne("usermapper.selectByUserName",username));
        return user;
    }
}
