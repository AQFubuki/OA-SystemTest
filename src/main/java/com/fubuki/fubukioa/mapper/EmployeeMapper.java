package com.fubuki.fubukioa.mapper;

import com.fubuki.fubukioa.entity.Employee;
import com.fubuki.fubukioa.utils.MyBatisUtils;

public class EmployeeMapper {
    public Employee SelectByEmployeeId(Long id) {
        Employee employee = (Employee) MyBatisUtils.executeQuery(
                sqlSession -> sqlSession.selectOne("employeemapper.selectByEmployeeId", id));
        return employee;
    }
}
