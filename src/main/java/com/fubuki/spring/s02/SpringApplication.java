package com.fubuki.spring.s02;

import com.fubuki.spring.s02.ioc.entity.Apple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:applicationContextS02.xml");
        Apple apple0 = context.getBean("Apple0", Apple.class);
        System.out.println("===============================");
        Apple apple1 = context.getBean("Apple1", Apple.class);
        System.out.println("===============================");
        Apple apple2 = context.getBean("Apple2", Apple.class);
        System.out.println("===============================");
        System.out.println(apple1.getTitle());
        System.out.println("===============================");
        System.out.println(apple2.getTitle());

        Apple apple4 = context.getBean("Apple4", Apple.class);
        Apple apple5 = (Apple) context.getBean("Apple5");
    }
}
