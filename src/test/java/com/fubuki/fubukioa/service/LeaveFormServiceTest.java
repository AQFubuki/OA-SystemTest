package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.LeaveForm;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

class LeaveFormServiceTest {

    LeaveFormService leaveFormService = new LeaveFormService();

    @Test
    void insert() {
        LeaveFormService l = new LeaveFormService();
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(4l); //员工编号
        form.setFormType(1); //事假
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;//起始时间
        Date endTime = null;//结束时间
        try {
            startTime = sdf.parse("2020-03-25 08:00:00");
            endTime = sdf.parse("2020-04-01 18:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        form.setStartTime(startTime);
        form.setEndTime(endTime);
        form.setReason("回家探亲");//请假事由
        form.setCreateTime(new Date());//创建时间
        form.setState("processing");//当前状态
        l.insert(form);
    }

    /**
     * 市场部员工请假单(72小时以上)测试用例
     *
     * @throws ParseException
     */
    @Test
    public void createLeaveForm1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(8l);
        form.setStartTime(sdf.parse("2020032608"));
        form.setEndTime(sdf.parse("2020040118"));
        form.setFormType(1);
        form.setReason("市场部员工请假单(72小时以上)");
        form.setCreateTime(new Date());
        LeaveForm savedForm = leaveFormService.createLeaveForm(form);
        System.out.println(savedForm.getFormId());
    }

    /**
     * 市场部员工请假单(72小时内)测试用例
     *
     * @throws ParseException
     */
    @Test
    public void createLeaveForm2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(8l);
        form.setStartTime(sdf.parse("2020032608"));
        form.setEndTime(sdf.parse("2020032718"));
        form.setFormType(1);
        form.setReason("市场部员工请假单(72小时内)");
        form.setCreateTime(new Date());
        LeaveForm savedForm = leaveFormService.createLeaveForm(form);
        System.out.println(savedForm.getFormId());
    }

    /**
     * 研发部部门经理请假单测试用例
     *
     * @throws ParseException
     */
    @Test
    public void createLeaveForm3() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(2l);
        form.setStartTime(sdf.parse("2020032608"));
        form.setEndTime(sdf.parse("2020040118"));
        form.setFormType(1);
        form.setReason("研发部部门经理请假单");
        form.setCreateTime(new Date());
        LeaveForm savedForm = leaveFormService.createLeaveForm(form);
        System.out.println(savedForm.getFormId());
    }

    /**
     * 总经理请假单测试用例
     *
     * @throws ParseException
     */
    @Test
    public void createLeaveForm4() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(11l);
        form.setStartTime(sdf.parse("2020032608"));
        form.setEndTime(sdf.parse("2020040118"));
        form.setFormType(1);
        form.setReason("总经理请假单");
        form.setCreateTime(new Date());
        LeaveForm savedForm = leaveFormService.createLeaveForm(form);
        System.out.println(savedForm.getFormId());
    }

    @Test
    void testInsert() {
    }

    @Test
    void selectByParams() {
        List<Map> process = leaveFormService.getLeaveFormList("process", 6l);
        for (Map map : process) {
            System.out.println(map);
        }
    }

    @Test
    void createLeaveForm() {
    }
}