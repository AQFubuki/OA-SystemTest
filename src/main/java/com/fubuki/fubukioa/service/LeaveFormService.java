package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Employee;
import com.fubuki.fubukioa.entity.LeaveForm;
import com.fubuki.fubukioa.entity.ProcessFlow;
import com.fubuki.fubukioa.mapper.LeaveFormMapper;
import com.fubuki.fubukioa.mapper.ProcessFlowMapper;
import com.fubuki.fubukioa.mapper.newEmployeeMapper;
import com.fubuki.fubukioa.utils.MyBatisUtils;

import java.util.Date;

public class LeaveFormService {
    private EmployeeService employeeService = new EmployeeService();

    public void insert(LeaveForm leaveForm) {
        MyBatisUtils.executeUpdate(sqlSession -> {
            LeaveFormMapper mapper = sqlSession.getMapper(LeaveFormMapper.class);
            mapper.insert(leaveForm);
            return null;
        });
    }

    public LeaveForm createLeaveForm(LeaveForm form) {
        LeaveForm f = (LeaveForm) MyBatisUtils.executeUpdate(sqlSession -> {
            newEmployeeMapper employeeMapper = sqlSession
                    .getMapper(newEmployeeMapper.class);
            Employee employee = employeeMapper.selectById(form.getEmployeeId());
            //1.持久化表单，8级以下员工表单状态为processing,8级状态为approved
            if (employee.getLevel() == 8) {
                form.setState("approved");
            } else {
                form.setState("processing");
            }
            LeaveFormMapper leaveFormMapper = sqlSession
                    .getMapper(LeaveFormMapper.class);
            leaveFormMapper.insert(form);
            // 2.增加第一条数据流程，说明表单已提交，状态为complete
            ProcessFlowMapper processFlowMapper = sqlSession
                    .getMapper(ProcessFlowMapper.class);
            ProcessFlow flow1 = new ProcessFlow();
            flow1.setFormId(form.getFormId());
            flow1.setOperatorId(employee.getEmployeeId());
            flow1.setAction("apply");
            flow1.setCreateTime(new Date());
            flow1.setOrderNo(1);
            flow1.setState("complete");
            flow1.setIsLast(0);
            processFlowMapper.insert(flow1);
            //3.分情况创建其余流程数据
            //3.1 7级以下员工,生成部门经理审批任务,请假时间大于等于72小时,还需生成总经理审批任务
            if (employee.getLevel() < 7) {
                Employee dmanager = employeeService
                        .selectLeader(employee.getEmployeeId());
                ProcessFlow flow2 = new ProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(dmanager.getEmployeeId());
                flow2.setAction("audit");
                flow2.setCreateTime(new Date());
                flow2.setOrderNo(2);
                flow2.setState("process");
                long diff = form.getEndTime().getTime()
                        - form.getStartTime().getTime();
                float hours = diff / (1000 * 60 * 60) * 1f;
                if (hours >= 72) {
                    flow2.setIsLast(0);
                    processFlowMapper.insert(flow2);
                    Employee manager = employeeService
                            .selectLeader(dmanager.getEmployeeId());
                    ProcessFlow flow3 = new ProcessFlow();
                    flow3.setFormId(form.getFormId());
                    flow3.setOperatorId(manager.getEmployeeId());
                    flow3.setAction("audit");
                    flow3.setCreateTime(new Date());
                    flow3.setState("ready");
                    flow3.setOrderNo(3);
                    flow3.setIsLast(1);
                    processFlowMapper.insert(flow3);
                } else {
                    flow2.setIsLast(1);
                    processFlowMapper.insert(flow2);
                }
            } else if (employee.getLevel() == 7) {
                //3.2 7级员工,仅生成总经理审批任务
                Employee manager = employeeService
                        .selectLeader(employee.getEmployeeId());
                ProcessFlow flow2 = new ProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(manager.getEmployeeId());
                flow2.setAction("audit");
                flow2.setCreateTime(new Date());
                flow2.setState("process");
                flow2.setOrderNo(2);
                flow2.setIsLast(1);
                processFlowMapper.insert(flow2);
            } else if (employee.getLevel() == 8) {
                //3.3 8级员工,生成总经理审批任务,系统自动通过
                ProcessFlow flow2 = new ProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(employee.getEmployeeId());
                flow2.setAction("audit");
                flow2.setResult("approved");
                flow2.setReason("自动通过");
                flow2.setCreateTime(new Date());
                flow2.setAuditTime(new Date());
                flow2.setState("complete");
                flow2.setOrderNo(2);
                flow2.setIsLast(1);
                processFlowMapper.insert(flow2);
            }
            return form;
        });
        return f;
    }
}
