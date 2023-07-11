package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Employee;
import com.fubuki.fubukioa.mapper.EmployeeMapper;
import com.fubuki.fubukioa.mapper.newEmployeeMapper;
import com.fubuki.fubukioa.utils.MyBatisUtils;

public class EmployeeService {
    private EmployeeMapper employeeMapper = new EmployeeMapper();

    /**
     * @param employeeId 传入用户ID
     * @return 根据用户ID返回该用户信息
     */
    public Employee selectById(Long employeeId) {
//        return employeeMapper.SelectByEmployeeId(employeeId);
        Employee employee = (Employee) MyBatisUtils.executeQuery(sqlSession -> {
            newEmployeeMapper employeeMapper = sqlSession.getMapper(newEmployeeMapper.class);
            return employeeMapper.selectById(employeeId);
        });
        return employee;
    }
}
