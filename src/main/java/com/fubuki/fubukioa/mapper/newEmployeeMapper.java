package com.fubuki.fubukioa.mapper;

import com.fubuki.fubukioa.entity.Employee;

import java.util.List;
import java.util.Map;

public interface newEmployeeMapper {
    public Employee selectById(Long employeeId);

    public List<Employee> selectByParams(Map params);
}
