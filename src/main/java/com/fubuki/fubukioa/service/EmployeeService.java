package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Employee;
import com.fubuki.fubukioa.mapper.newEmployeeMapper;
import com.fubuki.fubukioa.utils.MyBatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    //private EmployeeMapper employeeMapper = new EmployeeMapper();

    /**
     * @param employeeId 传入用户ID
     * @return 根据用户ID返回该用户信息
     */
    public Employee selectById(Long employeeId) {
//        return employeeMapper.SelectByEmployeeId(employeeId);
        Employee employee = (Employee) MyBatisUtils.executeQuery(sqlSession -> {
            newEmployeeMapper mapper = sqlSession.getMapper(newEmployeeMapper.class);
            return mapper.selectById(employeeId);
        });
        return employee;
    }

    public Employee selectLeader(Long employeeId) {
        Employee l = (Employee) MyBatisUtils.executeQuery(sqlSession -> {
            newEmployeeMapper mapper = sqlSession
                    .getMapper(newEmployeeMapper.class);
            Employee employee = mapper.selectById(employeeId);
            Map params = new HashMap<>();
            Employee leader = null;
            if (employee.getLevel() < 7) {
                //查询部门经理
                params.put("level", 7);
                params.put("departmentId", employee.getDepartmentId());
                List<Employee> employees = mapper.selectByParams(params);
                leader = employees.get(0);
            } else if (employee.getLevel() == 7) {
                //查询总经理
                params.put("level", 8);
                List<Employee> employees = mapper.selectByParams(params);
                leader = employees.get(0);
            } else if (employee.getLevel() == 8) {
                //返回自己
                leader = employee;
            }
            return leader;
        });
        return l;
    }
}
