package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.ProcessFlow;
import com.fubuki.fubukioa.mapper.ProcessFlowMapper;
import com.fubuki.fubukioa.utils.MyBatisUtils;

public class ProcessFlowService {
    public void insert(ProcessFlow processFlow) {
        MyBatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowMapper mapper = sqlSession.getMapper(ProcessFlowMapper.class);
            mapper.insert(processFlow);
            return null;
        });
    }
}
