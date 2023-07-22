package com.fubuki.spring.s07;

import org.springframework.stereotype.Repository;

//@Repository 组件类型注解默认beanId为类名首字母小写
//beanId=userDao
//也可以手动设置  @Repository("beanId")
@Repository("udao")
public class UserDao {
    public UserDao() {
        System.out.println("正在创建UserDao");
    }
}
