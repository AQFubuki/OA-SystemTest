package com.fubuki.spring.s07;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:acS07.xml");
//        String[] ids = context.getBeanDefinitionNames();
//        for (String id : ids) {
//            System.out.println(id+":"+context.getBean(id));
//        }

    }
}
