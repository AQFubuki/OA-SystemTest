package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Node;
import org.junit.jupiter.api.Test;

import java.util.List;

class RbacServiceTest {

    @Test
    void getNode() {
        Long l= 3L;
        RbacService rbacService=new RbacService();
        List<Node> list=rbacService.selectNodeByUserId(1L);
        for (Node node : list) {
            System.out.println(node);
        }

    }
}