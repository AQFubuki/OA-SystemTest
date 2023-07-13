package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Department;
import com.fubuki.fubukioa.mapper.DepartmentMapper;
import com.fubuki.fubukioa.utils.MyBatisUtils;

public class DepartmentService {
    public Department selectById(Long departmentId) {
        Department dep = (Department) MyBatisUtils.executeQuery(sqlSession -> {
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
            return mapper.selectById(departmentId);
        });
        return dep;
    }
}
