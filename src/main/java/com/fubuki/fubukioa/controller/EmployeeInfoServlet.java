package com.fubuki.fubukioa.controller;

import com.fubuki.fubukioa.entity.Employee;
import com.fubuki.fubukioa.service.EmployeeService;
import com.fubuki.fubukioa.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "EmployeeInfoServlet", value = "/api/emp_info")
public class EmployeeInfoServlet extends HttpServlet {
    private EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        Employee employee = employeeService.selectByEmployeeId(Long.parseLong(uid));
        ResponseUtils result = new ResponseUtils().put("employee", employee);
        //返回JSON结果
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(result.toJosnString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
