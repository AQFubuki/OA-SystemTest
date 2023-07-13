package com.fubuki.fubukioa.mapper;

import com.fubuki.fubukioa.entity.ProcessFlow;

import java.util.List;

public interface ProcessFlowMapper {
    public void insert(ProcessFlow processFlow);

    public void update(ProcessFlow processFlow);

    public List<ProcessFlow> selectByFormId(Long formId);
}
