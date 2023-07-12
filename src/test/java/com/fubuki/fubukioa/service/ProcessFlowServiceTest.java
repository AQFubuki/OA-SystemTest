package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.ProcessFlow;
import org.junit.jupiter.api.Test;

import java.util.Date;

class ProcessFlowServiceTest {

    @Test
    void insert() {
        ProcessFlow processFlow = new ProcessFlow();
        processFlow.setFormId(3l);
        processFlow.setOperatorId(2l);
        processFlow.setAction("audit");
        processFlow.setResult("approved");
        processFlow.setReason("同意");
        processFlow.setCreateTime(new Date());
        processFlow.setAuditTime(new Date());
        processFlow.setOrderNo(1);
        processFlow.setState("ready");
        processFlow.setIsLast(1);
        ProcessFlowService processFlowService = new ProcessFlowService();
        processFlowService.insert(processFlow);
    }
}