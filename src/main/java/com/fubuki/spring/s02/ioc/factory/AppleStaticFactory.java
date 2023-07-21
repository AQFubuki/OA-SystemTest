package com.fubuki.spring.s02.ioc.factory;

import com.fubuki.spring.s02.ioc.entity.Apple;

public class AppleStaticFactory {
    public static Apple createSweetApple() {
        Apple apple = new Apple();
        apple.setTitle("红富士3");
        apple.setColor("红色");
        apple.setOrigin("欧洲");
        return apple;
    }
}
