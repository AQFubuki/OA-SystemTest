package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Notice;
import com.fubuki.fubukioa.mapper.NoticeMapper;
import com.fubuki.fubukioa.utils.MyBatisUtils;

public class NoticeService {
    public void insert(Notice notice) {
        MyBatisUtils.executeUpdate(sqlSession -> {
            NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
            mapper.insert(notice);
            return null;
        });
    }
}
