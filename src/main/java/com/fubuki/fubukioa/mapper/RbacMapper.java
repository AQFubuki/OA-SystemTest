package com.fubuki.fubukioa.mapper;

import com.fubuki.fubukioa.entity.Node;
import com.fubuki.fubukioa.utils.MyBatisUtils;

import java.util.List;

public class RbacMapper {
    public List<Node> selectNodeByUserId(long userId) {
        List<Node> nodeList = (List<Node>) MyBatisUtils.executeQuery(
                sqlSession ->
                        sqlSession.selectList("rbacmapper.selectNodeByUserId", userId)
        );
        return nodeList;
    }
}
