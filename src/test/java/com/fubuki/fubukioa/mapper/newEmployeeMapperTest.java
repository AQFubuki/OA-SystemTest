package com.fubuki.fubukioa.mapper;

import com.fubuki.fubukioa.entity.Employee;
import com.fubuki.fubukioa.utils.MyBatisUtils;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

class newEmployeeMapperTest {

    @Test
    void selectById() {
    }

    @Test
    void selectByParams() {
        Map map = new LinkedHashMap();
        map.put("level", 8);
        // map.put("departmentId",2);
        Employee employee = (Employee) MyBatisUtils.executeQuery(sqlSession -> {
            newEmployeeMapper mapper = sqlSession.getMapper(newEmployeeMapper.class);
            return mapper.selectByParams(map).get(0);
        });
        System.out.println(employee);
    }
}