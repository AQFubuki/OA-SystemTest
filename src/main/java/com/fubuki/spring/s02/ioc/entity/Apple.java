package com.fubuki.spring.s02.ioc.entity;

public class Apple {
    private String title;
    private String color;
    private String origin;

    public Apple() {
        System.out.println("这是苹果的默认构造方法");
    }


    public Apple(String title, String color, String origin) {
        System.out.println("这是带参构造方法");
        this.title = title;
        this.color = color;
        this.origin = origin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        System.out.println("这是setTitle方法");
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        System.out.println("这是setColor方法");
        this.color = color;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        System.out.println("这是setOrigin方法");
        this.origin = origin;
    }
}
