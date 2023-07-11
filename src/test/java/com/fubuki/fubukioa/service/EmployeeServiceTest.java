package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Employee;
import org.junit.jupiter.api.Test;

class EmployeeServiceTest {

    @Test
    void selectByEmployeeId() {
        EmployeeService employeeService = new EmployeeService();
        Employee employee = employeeService.selectByEmployeeId(11L);
        System.out.println(employee);
    }
}