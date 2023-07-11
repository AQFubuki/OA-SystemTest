package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Employee;
import com.fubuki.fubukioa.mapper.EmployeeMapper;

public class EmployeeService {
    private EmployeeMapper employeeMapper = new EmployeeMapper();

    /**
     * @param employeeId 传入用户ID
     * @return 根据用户ID返回该用户信息
     */
    public Employee selectByEmployeeId(Long employeeId) {
        return employeeMapper.SelectByEmployeeId(employeeId);
    }
}
