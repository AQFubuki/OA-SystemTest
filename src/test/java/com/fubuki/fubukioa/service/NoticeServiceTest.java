package com.fubuki.fubukioa.service;

import com.fubuki.fubukioa.entity.Notice;
import org.junit.jupiter.api.Test;

class NoticeServiceTest {

    @Test
    void insert() {
        NoticeService noticeService = new NoticeService();
        noticeService.insert(new Notice(2l, "测试消息"));
    }
}