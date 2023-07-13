package com.fubuki.fubukioa.controller;

import com.fubuki.fubukioa.entity.Department;
import com.fubuki.fubukioa.entity.Employee;
import com.fubuki.fubukioa.entity.Node;
import com.fubuki.fubukioa.service.DepartmentService;
import com.fubuki.fubukioa.service.EmployeeService;
import com.fubuki.fubukioa.service.RbacService;
import com.fubuki.fubukioa.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserInfoServlet", value = "/api/user_info")
public class UserInfoServlet extends HttpServlet {
    private RbacService rbacService = new RbacService();
    private EmployeeService employeeService = new EmployeeService();
    private DepartmentService departmentService = new DepartmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String eid = request.getParameter("eid");
        List<Node> nodes = rbacService.selectNodeByUserId(Long.parseLong(uid));
        List<Map> treeList = new ArrayList<>();
        Map module = null;
        for (Node node : nodes) {
            if (node.getNodeType() == 1) {
                //1代表模块
                module = new LinkedHashMap();
                module.put("node", node);
                module.put("children", new ArrayList());
                treeList.add(module);
            } else if (node.getNodeType() == 2) {
                //2代表功能块
                List children = (List) module.get("children");
                children.add(node);
            }
        }

        Employee employee = employeeService.selectById(Long.parseLong(eid));
        Long did = employee.getDepartmentId();
        Department department = departmentService.selectById(did);
        System.out.println(department.getDepartmentName());
        ResponseUtils result = new ResponseUtils()
                .put("nodeList", treeList)
                .put("employee", employee)
                .put("department", department);

        //返回JSON结果
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(result.toJosnString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
