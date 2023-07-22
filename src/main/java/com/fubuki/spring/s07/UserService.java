package com.fubuki.spring.s07;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //@Autowired
    //如果装配注解放在属性上，Spring IoC容器会自动通过反射技术将private修饰符改成public
    //直接进行赋值，不再执行set方法
    private UserDao udao;

    public UserService() {
        System.out.println("正在创建UserService");
    }


    public UserService(UserDao udao) {
        this.udao = udao;
    }

    public UserDao getUdao() {
        return udao;
    }

    @Autowired
    //如果装配注解放在set方法上，则自动按类型/名称对set方法参数进行注入
    public void setUdao(UserDao udao) {
        System.out.println("setUdao:" + udao);
        this.udao = udao;
    }


}
