package com.fubuki.fubukioa.controller;

import com.fubuki.fubukioa.entity.LeaveForm;
import com.fubuki.fubukioa.service.LeaveFormService;
import com.fubuki.fubukioa.utils.ChangeLocalDateTime;
import com.fubuki.fubukioa.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "LeaveFormServlet", value = "/api/leave/*")
public class LeaveFormServlet extends HttpServlet {
    private LeaveFormService leaveFormService = new LeaveFormService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        //http://localhost/api/leave/create
        String uri = request.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        if (methodName.equals("create")) {
            this.create(request, response);
        } else if (methodName.equals("list")) {
            this.list(request, response);
        } else if (methodName.equals("audit")) {
            this.audit(request, response);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strEmployeeId = request.getParameter("eid");
        String formType = request.getParameter("formType");
        //从1970年到现在的毫秒数
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String reason = request.getParameter("reason");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(Long.parseLong(strEmployeeId));
        form.setStartTime(new Date(Long.parseLong(startTime)));
        form.setEndTime(new Date(Long.parseLong(endTime)));
        form.setFormType(Integer.parseInt(formType));
        form.setReason(reason);
        form.setCreateTime(new Date());
        ResponseUtils resp = null;
        try {
            leaveFormService.createLeaveForm(form);
            resp = new ResponseUtils();
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(), e.getMessage());
        }

        response.getWriter().println(resp.toJosnString());
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("eid");
        ResponseUtils resp = null;
        try {
            List<Map> formList = leaveFormService
                    .getLeaveFormList("process",
                            Long.parseLong(employeeId));

            //这里要将数据库传回来的LocalDateTime转换成Date，否则JSON将无法转化
            if (!formList.isEmpty()) {
                for (Map map : formList) {
                    System.out.println(map);
                    String start_time = ChangeLocalDateTime
                            .changeToString((LocalDateTime) map.get("start_time"));
                    String end_time = ChangeLocalDateTime
                            .changeToString((LocalDateTime) map.get("end_time"));
                    String create_time = ChangeLocalDateTime
                            .changeToString((LocalDateTime) map.get("create_time"));
                    map.replace("create_time", create_time);
                    map.replace("start_time", start_time);
                    map.replace("end_time", end_time);
                }
            }

            resp = new ResponseUtils().put("list", formList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(), e.getMessage());
        }
        response.getWriter().println(resp.toJosnString());
    }

    private void audit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String formId = request.getParameter("formId");
        String operatorId = request.getParameter("operatorId");
        String result = request.getParameter("result");
        String reason = request.getParameter("reason");
        System.out.println(formId + " " + operatorId + " " + result + " " + reason);

        ResponseUtils resp = null;
        try {
            leaveFormService.audit(Long.parseLong(formId),
                    Long.parseLong(operatorId), result, reason);
            resp = new ResponseUtils();
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(), e.getMessage());
        }
        response.getWriter().println(resp.toJosnString());
    }
}
