package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.LeaveForm;
import com.fubuki.fubukioa.mapper.LeaveFormMapper;
import com.fubuki.fubukioa.utils.MyBatisUtils;

public class LeaveFormService {
    public void insert(LeaveForm leaveForm) {
        MyBatisUtils.executeUpdate(sqlSession -> {
            LeaveFormMapper mapper = sqlSession.getMapper(LeaveFormMapper.class);
            mapper.insert(leaveForm);
            return null;
        });
    }
}
