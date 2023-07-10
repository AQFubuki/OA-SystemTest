package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Node;
import com.fubuki.fubukioa.mapper.RbacMapper;

import java.util.List;

public class RbacService {
    private RbacMapper rbacMapper=new RbacMapper();

    /**
     *
     * @param userId 传入用户ID
     * @return 根据用户ID返回该用户拥有权限的功能块
     */
    public List<Node> selectNodeByUserId(Long userId){
        return rbacMapper.selectNodeByUserId(userId);
    }
}
