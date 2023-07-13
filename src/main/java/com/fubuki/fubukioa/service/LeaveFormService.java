package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Employee;
import com.fubuki.fubukioa.entity.LeaveForm;
import com.fubuki.fubukioa.entity.ProcessFlow;
import com.fubuki.fubukioa.mapper.LeaveFormMapper;
import com.fubuki.fubukioa.mapper.ProcessFlowMapper;
import com.fubuki.fubukioa.mapper.newEmployeeMapper;
import com.fubuki.fubukioa.service.exception.LeaveFormException;
import com.fubuki.fubukioa.utils.MyBatisUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaveFormService {
    private EmployeeService employeeService = new EmployeeService();

    public void insert(LeaveForm leaveForm) {
        MyBatisUtils.executeUpdate(sqlSession -> {
            LeaveFormMapper mapper = sqlSession.getMapper(LeaveFormMapper.class);
            mapper.insert(leaveForm);
            return null;
        });
    }

    /**
     * @param pfState      审批流程的状态
     * @param pfOperatorId 审批经办人的ID
     * @return 返回ID所有需要处理的表单
     */
    public List<Map> getLeaveFormList(String pfState, Long pfOperatorId) {
        return (List<Map>) MyBatisUtils.executeQuery(sqlSession -> {
            LeaveFormMapper mapper = sqlSession.getMapper(LeaveFormMapper.class);
            return mapper.selectByParams(pfState, pfOperatorId);
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

    /**
     * 审核请假单
     *
     * @param formId     表单编号
     * @param operatorId 经办人(当前登录员工)
     * @param result     审批结果
     * @param reason     审批意见
     */
    public void audit(Long formId, Long operatorId, String result, String reason) {
        MyBatisUtils.executeUpdate(sqlSession -> {
            //无论同意/驳回,当前任务状态变更为complete
            ProcessFlowMapper processFlowMapper = sqlSession.getMapper(ProcessFlowMapper.class);
            List<ProcessFlow> flowList = processFlowMapper.selectByFormId(formId);
            if (flowList.size() == 0) {
                throw new LeaveFormException("无效的审批流程");
            }
//            //获取当前任务ProcessFlow对象
            List<ProcessFlow> processList = flowList.stream().filter(p ->
                            p.getOperatorId() == operatorId
                                    && p.getState().equals("process"))
                    .collect(Collectors.toList());
            ProcessFlow process = null;
            if (processList.size() == 0) {
                throw new LeaveFormException("未找到待处理任务节点");
            } else {
                process = processList.get(0);
                process.setState("complete");
                process.setResult(result);
                process.setReason(reason);
                process.setAuditTime(new Date());
                processFlowMapper.update(process);
            }

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");
            LeaveFormMapper leaveFormMapper = sqlSession.getMapper(LeaveFormMapper.class);
            //  NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
            LeaveForm form = leaveFormMapper.selectById(formId);
            Employee operator = employeeService.selectById(operatorId);
            Employee employee = employeeService.selectById(form.getEmployeeId());
            //如果当前任务是最后一个节点,代表流程结束,更新请假单状态为对应的approved/refused
            if (process.getIsLast() == 1) {
                form.setState(result); //approved / refused
                leaveFormMapper.update(form);
                String strResult = null;
                if (result.equals("approved")) {
                    strResult = "批准";
                } else if (result.equals("refused")) {
                    strResult = "驳回";
                }

//                String notice1 = String.format("您的请假申请[%s-%s]%s%s已%s,审批意见:%s,审批流程已结束"
//                        ,sdf.format(form.getStartTime()),sdf.format(form.getEndTime())
//                        ,operator.getTitle(),operator.getName() ,strResult,reason
//                );
//                noticeMapper.insert(new Notice(form.getEmployeeId(), notice1));
//
//                String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已%s,审批意见:%s,审批流程已结束" ,
//                        employee.getTitle() , employee.getName() , sdf.format( form.getStartTime()) , sdf.format(form.getEndTime()),
//                        strResult , reason);//发给审批人的通知
//                noticeMapper.insert(new Notice(operator.getEmployeeId(),notice2));
//
            } else {
                ////readyList包含所有后续任务节点
                List<ProcessFlow> readyList = flowList.stream()
                        .filter(p -> p.getState().equals("ready"))
                        .collect(Collectors.toList());
                //如果当前任务不是最后一个节点且审批通过,那下一个节点的状态从ready变为process
                if (result.equals("approved")) {
                    ProcessFlow readyProcess = readyList.get(0);
                    readyProcess.setState("process");
                    processFlowMapper.update(readyProcess);
//                    //消息1: 通知表单提交人,部门经理已经审批通过,交由上级继续审批
//                    String notice1 = String.format("您的请假申请[%s-%s]%s%s已批准,审批意见:%s ,请继续等待上级审批" ,
//                            sdf.format(form.getStartTime()) , sdf.format(form.getEndTime()),
//                            operator.getTitle() , operator.getName(),reason);
//                    noticeMapper.insert(new Notice(form.getEmployeeId(),notice1));
//
//                    //消息2: 通知总经理有新的审批任务
//                    String notice2 = String.format("%s-%s提起请假申请[%s-%s],请尽快审批" ,
//                            employee.getTitle() , employee.getName() , sdf.format( form.getStartTime()) , sdf.format(form.getEndTime()));
//                    noticeMapper.insert(new Notice(readyProcess.getOperatorId(),notice2));
//
//                    //消息3: 通知部门经理(当前经办人),员工的申请单你已批准,交由上级继续审批
//                    String notice3 = String.format("%s-%s提起请假申请[%s-%s]您已批准,审批意见:%s,申请转至上级领导继续审批" ,
//                            employee.getTitle() , employee.getName() , sdf.format( form.getStartTime()) , sdf.format(form.getEndTime()), reason);
//                    noticeMapper.insert(new Notice(operator.getEmployeeId(),notice3));
//
                } else if (result.equals("refused")) {
                    //如果当前任务不是最后一个节点且审批驳回,则后续所有任务状态变为cancel,请假单状态变为refused
                    for (ProcessFlow p : readyList) {
                        p.setState("cancel");
                        processFlowMapper.update(p);
                    }
                    form.setState("refused");
                    leaveFormMapper.update(form);
//                    //消息1: 通知申请人表单已被驳回
//                    String notice1 = String.format("您的请假申请[%s-%s]%s%s已驳回,审批意见:%s,审批流程已结束" ,
//                            sdf.format(form.getStartTime()) , sdf.format(form.getEndTime()),
//                            operator.getTitle() , operator.getName(),reason);
//                    noticeMapper.insert(new Notice(form.getEmployeeId(),notice1));
//
//                    //消息2: 通知经办人表单"您已驳回"
//                    String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已驳回,审批意见:%s,审批流程已结束" ,
//                            employee.getTitle() , employee.getName() , sdf.format( form.getStartTime()) , sdf.format(form.getEndTime()), reason);
//                    noticeMapper.insert(new Notice(operator.getEmployeeId(),notice2));
                }

            }
            return null;
        });
    }
}
