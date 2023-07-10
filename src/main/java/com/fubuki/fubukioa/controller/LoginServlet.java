package com.fubuki.fubukioa.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fubuki.fubukioa.entity.User;
import com.fubuki.fubukioa.service.UserService;
import com.fubuki.fubukioa.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet", value = "/api/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        //接收用户输入
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //调用业务逻辑
        //Map result = new LinkedHashMap();
        ResponseUtils result=null;
        try {
            User user = userService.checkLogin(username, password);
            //处理结果编码，0代表处理成功，非零代表处理失败
            result=new ResponseUtils().put("user",user);
        } catch (Exception e) {
            e.printStackTrace();
            result=new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        //返回JSON结果
        response.getWriter().println(result.toJosnString());

    }
}
