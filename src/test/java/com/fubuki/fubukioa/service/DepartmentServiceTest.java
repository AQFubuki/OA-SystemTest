package com.fubuki.fubukioa.service;

import org.junit.jupiter.api.Test;

class DepartmentServiceTest {

    @Test
    void selectById() {
        DepartmentService departmentService = new DepartmentService();
        departmentService.selectById(1l);
    }
}