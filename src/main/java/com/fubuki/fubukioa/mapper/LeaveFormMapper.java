package com.fubuki.fubukioa.mapper;

import com.fubuki.fubukioa.entity.LeaveForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LeaveFormMapper {

    public void insert(LeaveForm form);

    //public List<Map> selectByParams(Map params);
//    @Param("pf_state") String 相当于map.put("pf_xxxx",xxxx);
    public List<Map> selectByParams(
            @Param("pf_state") String pfState,
            @Param("pf_operator_id") Long pfOperatorId);

    public void update(LeaveForm form);

    public LeaveForm selectById(Long id);
}

