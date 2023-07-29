package com.fubuki.spring.controller;

import com.fubuki.spring.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/um")
public class UrlMappingController {
    @GetMapping("/g")
    @ResponseBody
    public String getMethod(
            @RequestParam("employee_name")
            String employeeName) {
        return employeeName + "get请求";
    }

    @PostMapping("/p")
    @ResponseBody
    public String postMethod(String username, String password) {
        return String.format("[%s]:%d", username, password);
    }

    @PostMapping("/p1")
    @ResponseBody
    public String postMethod1(User user, Date createTime) {
        System.out.println(user.getUsername() + user.getPassword() + "[]" + createTime);
        return user.getUsername() + user.getPassword() + "[]" + createTime;
    }

}
